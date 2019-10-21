package com.didispace.scca.rest.web;

import com.didispace.scca.core.domain.Env;
import com.didispace.scca.core.domain.Label;
import com.didispace.scca.core.domain.Project;
import com.didispace.scca.rest.dto.EnvDto;
import com.didispace.scca.rest.dto.LabelDto;
import com.didispace.scca.rest.dto.ProjectDto;
import com.didispace.scca.rest.dto.base.WebResp;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeanUtils;
import org.springframework.data.domain.Example;
import org.springframework.security.access.annotation.Secured;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Assert;
import org.springframework.web.bind.annotation.*;

import java.util.*;

/**
 * Created by 程序猿DD/翟永超 on 2018/4/27.
 * <p>
 * Blog: http://blog.didispace.com/
 * Github: https://github.com/dyc87112/
 */
@Api("Project MGT（项目管理）")
@Slf4j
@RestController
@RequestMapping("${scca.rest.context-path:}/project")
public class ProjectController extends BaseController {

    @ApiOperation("List Project / 项目列表")
    @RequestMapping(method = RequestMethod.GET)
    public WebResp<List<ProjectDto>> findAll() {
//        Pageable pageable = new PageRequest(page, size);
        List<ProjectDto> result = new ArrayList<>();
        for (Project project : projectRepo.findAll()) {
            ProjectDto dto = new ProjectDto();
            BeanUtils.copyProperties(project, dto, "envs", "labels");

            for (Env env : project.getEnvs()) {
                EnvDto envDto = new EnvDto();
                BeanUtils.copyProperties(env, envDto);
                dto.getEnvs().add(envDto);
            }

            for (Label label : project.getLabels()) {
                LabelDto labelDto = new LabelDto();
                BeanUtils.copyProperties(label, labelDto);
                dto.getLabels().add(labelDto);
            }

            result.add(dto);
        }
        return WebResp.success(result);
    }

    @ApiOperation("Project Detail / 项目详情")
    @RequestMapping(path = "/detail", method = RequestMethod.GET)
    public WebResp<ProjectDto> findProjectDetail(@RequestParam("id") String id) {
        Project project = new Project();
        project.setId(id);
        Example<Project> example = Example.of(project);
        Optional<Project> op = projectRepo.findOne(example);
        project = op.get();
        ProjectDto dto = new ProjectDto();
        BeanUtils.copyProperties(project, dto);

        return WebResp.success(dto);
    }

    @Transactional
    @ApiOperation("Create Project / 创建项目")
    @RequestMapping(method = RequestMethod.POST)
    @Secured("ROLE_ADMIN")
    public WebResp<String> create(@RequestBody ProjectDto project) {
        log.info("create Project : " + project);

        Project saveProject = new Project();
        BeanUtils.copyProperties(project, saveProject, "envs", "labels");
        // 关联环境（env)
        for (EnvDto envDto : project.getEnvs()) {
            Optional<Env> optional =  envRepo.findById(envDto.getId());
            Env env = optional.get();
            saveProject.getEnvs().add(env);
        }
        saveProject = projectRepo.save(saveProject);

        // 关联默认版本（label）
        Label label = new Label();
        label.setName(sccaProperties.getDefaultLabel());
        label.setProject(saveProject);
        labelRepo.save(label);

        return WebResp.success("create Project success");
    }

    @Transactional
    @ApiOperation("Delete Project / 删除项目")
    @RequestMapping(method = RequestMethod.DELETE)
    @Secured("ROLE_ADMIN")
    public WebResp<String> deleteProject(@RequestParam("id") String id) {
        Project project = new Project();
        project.setId(id);
        Example<Project> example = Example.of(project);
        Optional<Project> optional = projectRepo.findOne(example);
        project =optional.get();
        Assert.notNull(project, "Project [" + id + "] not exist");

        log.info("delete Project : " + project.getName());

        // 级联删除配置版本的数据与实际配置存储
        persistenceService.deletePropertiesByProject(project.getName());

        // 删除scca中的管理实体
        projectRepo.delete(project);

        return WebResp.success("delete Project success");
    }

    @Transactional
    @ApiOperation("Update Project / 更新项目")
    @RequestMapping(method = RequestMethod.PUT)
    @Secured("ROLE_ADMIN")
    public WebResp<String> updateProject(@RequestBody ProjectDto project) {
        Project tmp = new Project();
        tmp.setId(project.getId());
        Example<Project> example = Example.of(tmp);
        Optional<Project> optional = projectRepo.findOne(example);
        Project updateProject = optional.get();
        Assert.notNull(updateProject, "Project [" + project.getId() + "] not exist");




        // 更新项目名称
        updateProject.setName(project.getName());

        // 更新项目需要移除和添加的环境清单
        List<Env> removeList = new ArrayList<>();
        List<Env> addList = new ArrayList<>();

        Set<String> haveSet = new HashSet<>();
        project.getEnvs().forEach(p -> haveSet.add(p.getId()));

        // 找出要移除的环境
        for (Env env : updateProject.getEnvs()) {
            boolean remove = false;
            for (EnvDto envDto : project.getEnvs()) {
//                if (env.getId().equals( envDto.getId())) {
//                    remove = false;
//                    break;
//                }
                if(haveSet.contains(envDto.getId())){
                    remove = true;
                }
            }

            if (remove) { // 移除这个环境
                removeList.add(env);
            }
        }

        // 找出要新关联的环境
        for (EnvDto envDto : project.getEnvs()) {
            boolean add = true;
            for (Env env : updateProject.getEnvs()) {
                if (env.getId() == envDto.getId()) {
                    add = false;
                    break;
                }
            }

            if (add) { // 添加这个环境
                Optional<Env> optional1 = envRepo.findById(envDto.getId());
                Env e = optional1.get();
                addList.add(e);
            }
        }

        updateProject.getEnvs().removeAll(removeList);
        updateProject.getEnvs().addAll(addList);

        projectRepo.save(updateProject);

        // 联动删除实际存储的操作
//        for (Env env : removeList) {
//            persistenceService.deletePropertiesByProjectAndEnv(updateProject.getName(), env.getName());
//        }

        return WebResp.success("update Project success");
    }

    @Transactional
    @ApiOperation("Project Add Label / 项目增加配置版本")
    @RequestMapping(path = "/label", method = RequestMethod.POST)
    @Secured("ROLE_ADMIN")
    public WebResp<LabelDto> addProjectLabel(@RequestParam("projectId") String projectId,
                                             @RequestParam("labelName") String labelName) {
        Project tmp = new Project();
        tmp.setId(projectId);
        Example<Project> example = Example.of(tmp);
        Optional<Project> optional = projectRepo.findOne(example);
        Project owner = optional.get();
        Assert.notNull(owner, "Project [" + projectId + "] not exist");

        Label label = new Label();
        label.setName(labelName);
        label.setProject(owner);
        label = labelRepo.save(label);

        LabelDto dto = new LabelDto();
        BeanUtils.copyProperties(label, dto);

        return WebResp.success(dto, "create project [" + owner.getName() + "] label [" + labelName + "] success");
    }


    @Transactional
    @ApiOperation("Project Delete Label / 项目删除版本")
    @RequestMapping(path = "/label", method = RequestMethod.DELETE)
    @Secured("ROLE_ADMIN")
    public WebResp<String> deleteProjectLabel(@RequestParam("labelId") String labelId) {
        Label label = new Label();
        label.setId(labelId);
        Example<Label> example = Example.of(label);
        Optional<Label> optional = labelRepo.findOne(example);
        label = optional.get();
        Assert.notNull(label, "Label [" + labelId + "] not exist");

        log.info("delete Label [{}-{}]", label.getProject().getName(), label.getName());

        // TODO 同步删除配置的存储
        persistenceService.deletePropertiesByLabel(label);

        // 删除scca中的管理实体
        labelRepo.delete(label);

        return WebResp.success("delete project [" + label.getProject().getName() + "] label [" + label.getName() + "] success");
    }

    @Transactional
    @ApiOperation("Project Add Env / 项目增加环境")
    @RequestMapping(path = "/env", method = RequestMethod.POST)
    @Secured("ROLE_ADMIN")
    public WebResp<String> addProjectEnv(@RequestParam("projectId") String projectId, @RequestParam("envId") String envId) {
        Project owner = new Project();
        owner.setId(projectId);
        Example<Project> example=Example.of(owner);
        Optional<Project> optional = projectRepo.findOne(example);
        owner = optional.get();
        Assert.notNull(owner, "Project [" + projectId + "] not exist");

        Env env = new Env();
        env.setId(envId);
        Example<Env> example1 = Example.of(env);
        Optional<Env> optional1 = envRepo.findOne(example1);
        env = optional1.get();
        Assert.notNull(env, "Env [" + envId + "] not exist");

        owner.getEnvs().add(env);
        projectRepo.save(owner);

        return WebResp.success("add project [" + owner.getName() + "] env [" + env.getName() + "] success");
    }

    @Transactional
    @ApiOperation("Project Remove Env / 项目移除环境")
    @RequestMapping(path = "/env", method = RequestMethod.DELETE)
    @Secured("ROLE_ADMIN")
    public WebResp<String> deleteProjectEnv(@RequestParam("projectId") String projectId, @RequestParam("envId") String envId) {
        Project owner = new Project();
        owner.setId(projectId);
        Example<Project> example=Example.of(owner);
        Optional<Project> optional = projectRepo.findOne(example);
        owner = optional.get();
        Assert.notNull(owner, "Project [" + projectId + "] not exist");

        Env env = new Env();
        env.setId(envId);
        Example<Env> example1 = Example.of(env);
        Optional<Env> optional1 = envRepo.findOne(example1);
        env = optional1.get();
        Assert.notNull(env, "Env [" + envId + "] not exist");

        owner.getEnvs().remove(env);
        projectRepo.save(owner);

        // 删除持久化内容
        for (Label label : owner.getLabels()) {
            persistenceService.deletePropertiesByLabel(label);
        }

        return WebResp.success("remove project [" + owner.getName() + "] env [" + env.getName() + "] success");
    }

}
