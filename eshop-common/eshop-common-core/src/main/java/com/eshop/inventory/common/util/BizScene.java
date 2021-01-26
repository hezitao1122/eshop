//
//package com.eshop.inventory.common.util;
//
//import com.eshop.inventory.common.dto.BaseDTO;
//import com.eshop.inventory.common.entity.BaseEntity;
//import com.eshop.inventory.common.enums.SortEnum;
//import org.apache.commons.lang.StringUtils;
//
//import java.util.ArrayList;
//import java.util.HashMap;
//import java.util.List;
//import java.util.Map;
//import java.util.TreeMap;
//
//
///**
// * lassName: AbsScene <br/>
// * Function: 业务场景基类对象, <br/>
// * Date: 2018年7月10日 下午3:44:40 <br/>
// *
// * @author Zzj
// * @version 1.0
// * @see
// * @since JDK 1.8
// */
//public abstract class BizScene {
//
//    /**
//     * 树型列表组装方法
//     *
//     * @param sourceList
//     * @param pos
//     * @param parentId
//     * @param targetTree
//     * @param targetClazz
//     * @throws Exception
//     */
//    protected <T> void fetchTreeHierarchical(List<? extends AbsModel> sourceList, int pos, String parentId,
//                                             List<T> targetTree, Class<T> targetClazz) throws Exception {
//        if (sourceList.isEmpty())
//            throw new Exception("源数据不能为空");
//        if (parentId.equals(""))
//            throw new Exception("树的根节点异常");
//        String flag = parentId;
//        List<?> tempList = sourceList.subList(pos, sourceList.size());
//        for (int i = 0; i < tempList.size(); i++) {
//            // 罗列要使用的数据
//            String curIdFieldName = ReflectUtil.getFieldNameByAnnotation(tempList.get(i), EwTreeNodeId.class);
//            if (StringUtils.isEmpty(curIdFieldName))
//                curIdFieldName = "ewId";
//            String curId = ReflectUtil.getValueByFieldName(tempList.get(i), curIdFieldName).toString();
//            String curParentId = ReflectUtil.getValueByFieldName(tempList.get(i), //
//                    ReflectUtil.getFieldNameByAnnotation(tempList.get(i), EwTreeParentNodeId.class)).toString();
//            // 组装前端要使用的数据结构
//            T treeNode = targetClazz.newInstance();
//            ReflectUtil.invokeMethod(treeNode, ReflectUtil.methodNameByAnnotation(targetClazz, EwTreeDataSetter.class),
//                    tempList.get(i));
//            if (curParentId.equals(parentId)) {
//                targetTree.add(treeNode);
//                flag = curId;
//            }
//            if (!curParentId.equals(parentId) && curParentId.equals(flag)) {
//                List<T> childTree = new ArrayList<T>();
//                treeNode = targetTree.get(targetTree.size() - 1);
//                if (StringUtils.isEmpty(ReflectUtil.getValueByFieldName(treeNode, //
//                        ReflectUtil.getFieldByAnnotation(treeNode, EwTreeChildren.class)))) {
//                    ReflectUtil.setValueByFieldName(treeNode, //
//                            ReflectUtil.getFieldByAnnotation(treeNode, EwTreeChildren.class), //
//                            childTree);
//                    fetchTreeHierarchical(sourceList, pos, curParentId, childTree, targetClazz);
//                }
//            }
//        }
//    }
//
//    /**
//     * @param list      需要遍历成树的集合
//     * @param clazz1    返回值List的泛型
//     * @param pos       根节点标识
//     * @param parentId  根节点的父节点
//     * @param sortField 需要排序的字段
//     * @param sortEnum  排序的方式
//     * @Description: 根据集合遍历成树, 树可以有多个根
//     * ----------------------------
//     * @Method: listToTree
//     * @Author: zeryts
//     * @Return: java.util.List<T>
//     * @Date: Create in 2019/5/7 20:30
//     */
//    protected <T extends BaseDTO> List<T> listToTree(List<? extends BaseEntity> list, Class<T> clazz1, int pos, String parentId, String sortField, SortEnum sortEnum) throws Exception {
//        if (list.isEmpty()) {
//            throw new Exception("源数据不能为空");
//        } else if (parentId.equals("")) {
//            throw new Exception("树的根节点异常");
//        }
//        Map<String, List> m = new HashMap<>();
//        for (Object obj : list) {
//            //获取Class对象
//            String treePath = ReflectUtil.getValueByFieldName(obj, "treePath").toString();
//            String oParentId = ReflectUtil.getValueByFieldName(obj, "parentId").toString();
//            if ((oParentId).equals(parentId)) {
//                //是根节点
//                //获取该对象的主键
//                //获取拼接treePath的节点id所有方法
//                String curIdFieldName = ReflectUtil.getFieldNameByAnnotation(obj, EwTreeNodeId.class);
//                if (StringUtil.isEmpty(curIdFieldName)) {
//                    curIdFieldName = "ewId";
//                }
//                //获取到拼接的id
//                String curId = ReflectUtil.getValueByFieldName(obj, curIdFieldName).toString();
//                String k = "/" + oParentId + "/" + curId;
//                if (m.get(k) != null) {
//                    m.get(k).add(obj);
//                } else {
//                    List lis = new ArrayList<>();
//                    lis.add(obj);
//                    m.put(k, lis);
//                }
//            } else {
//                //如果该节点属于此根,加到该集合下去
//                //String rootTreePath = treePath.substring(0,treePath.indexOf("/",3));
//                String selfStr = "/" + parentId + "/";
//                int i = treePath.indexOf(selfStr) + selfStr.length();
//                int j = treePath.indexOf("/", i);
//                String str = treePath.substring(treePath.indexOf(selfStr), j);
//                List res = m.get(str);
//                if (res != null) {
//                    res.add(obj);
//                } else {
//                    //生成一个集合
//                    List lis = new ArrayList<>();
//                    lis.add(obj);
//                    m.put(str, lis);
//                }
//            }
//        }
//        List<T> allList = new ArrayList<>();
//        for (String key : m.keySet()) {
//            //获取到集合
//            List<AbsModel> lis = m.get(key);
//            List<AbsModel> absModes = new ArrayList<>(lis.size());
//
//            Map<String, AbsModel> am = new TreeMap<>();
//            for (AbsModel o : lis) {
//                //获取Class对象
//                String treePath = ReflectUtil.getValueByFieldName(o, "treePath").toString();
//                //获取拼接treePath的节点id所有方法
//                String curIdFieldName = ReflectUtil.getFieldNameByAnnotation(o, EwTreeNodeId.class);
//                if (StringUtils.isEmpty(curIdFieldName)) {
//                    curIdFieldName = "ewId";
//                }
//                //获取到拼接的id
//                String curId = ReflectUtil.getValueByFieldName(o, curIdFieldName).toString();
//                String k = treePath + curId;
//                am.put(k, o);
//            }
//            for (Map.Entry<String, AbsModel> entry : am.entrySet()) {
//                absModes.add(entry.getValue());
//            }
//            List<T> l = new ArrayList<>();
//            this.fetchTreeHierarchical(absModes, 0, parentId, l, clazz1);
//            allList.addAll(l);
//        }
//        return allList;
//    }
//
//    /**
//     * @param list     需要遍历成树的集合
//     * @param clazz1   返回值List的泛型
//     * @param pos      根节点标识
//     * @param parentId 根节点的父节点
//     * @Description: 根据集合遍历成树, 树可以有多个根
//     * ----------------------------
//     * @Method: listToTree
//     * @Author: zeryts
//     * @Return: java.util.List<T>
//     * @Date: Create in 2019/5/7 20:30
//     */
//    protected <T extends BaseDTO> List<T> listToTree(List<? extends BaseEntity> list, Class<T> clazz1, int pos, String parentId) throws Exception {
//        return listToTree(list, clazz1, pos, parentId, null, null);
//    }
//
//    /**
//     * @param list  需要排序的list集合
//     * @param field 根据排序的字段
//     * @Description: 根据某个字段对list进行排序的方法
//     * ----------------------------
//     * @Method: listSortByField
//     * @Author: zeryts
//     * @Return: void
//     * @Date: Create in 2019/6/3 15:34
//     */
//    public void listSortByField(List<? extends BaseDTO> list, String field, SortEnum sortEnum) throws Exception {
//        if (list.isEmpty()) {
//            throw new Exception("源数据不能为空");
//        } else if (field.equals("")) {
//            throw new Exception("排序字段不能为空");
//        }
//
//        for (Object obj : list) {
//            Object s = ReflectUtil.getValueByFieldName(obj, field);
//            if (s == null)
//                throw new Exception("对象的排序字段不存在或者为空");
//        }
//        list.sort((x, y) -> {
//            try {
//                Object s = ReflectUtil.getValueByFieldName(x, field);
//                Object z = ReflectUtil.getValueByFieldName(y, field);
//                if (sortEnum == SortEnum.ASC) {
//                    return Integer.compare(Integer.valueOf(s.toString()), Integer.valueOf(z.toString()));
//                } else if (sortEnum == SortEnum.DESC) {
//                    return Integer.compare(Integer.valueOf(z.toString()), Integer.valueOf(s.toString()));
//                }
//            } catch (Exception e) {
//                e.printStackTrace();
//            }
//            return 0;
//        });
//    }
//
//}
