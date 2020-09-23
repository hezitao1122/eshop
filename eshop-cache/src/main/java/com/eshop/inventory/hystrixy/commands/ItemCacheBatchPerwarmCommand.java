package com.eshop.inventory.hystrixy.commands;

import com.eshop.inventory.common.dto.ResultDto;
import com.eshop.inventory.manage.item.dto.TbItemDTO;
import com.eshop.inventory.manage.item.feign.ItemFeign;
import com.netflix.hystrix.HystrixCommandGroupKey;
import com.netflix.hystrix.HystrixObservableCommand;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import rx.Observable;
import rx.Subscriber;
import rx.schedulers.Schedulers;

import java.util.List;

/**
 * @author zeryts
 * @description: HystrixObservableCommand的实现，暂时仅仅用于批量查询并且拼接的操作
 * ```````````````````````````
 * @title: ItemCacheBatchPerwarmCommand
 * @projectName inventory
 * @date 2020/5/12 22:10
 */
@Slf4j
public class ItemCacheBatchPerwarmCommand extends HystrixObservableCommand<TbItemDTO> {

    private List<Long> ids;

    @Autowired
    private ItemFeign itemFeign;

    public ItemCacheBatchPerwarmCommand(List<Long> ids) {
        super(HystrixCommandGroupKey.Factory.asKey("ItemCachePerwarmCommandGroup"));
        this.ids = ids;
    }

    @Override
    protected Observable<TbItemDTO> construct() {
        return Observable.create(new Observable.OnSubscribe<TbItemDTO>() {
            @Override
            public void call(Subscriber<? super TbItemDTO> subscriber) {
                try {
                    for (Long id : ids) {
                        ResultDto<TbItemDTO> result = itemFeign.find(id);
                        // ON NEXT 代表执行完这次继续下一次
                        subscriber.onNext(result.getData());
                    }
                    // ON Completed 代表结束返回的拼接
                    subscriber.onCompleted();
                } catch (Exception e) {
                    subscriber.onError(e);
                    log.info(e.toString(), e);
                }
            }
        }).subscribeOn(Schedulers.io());
    }
}
