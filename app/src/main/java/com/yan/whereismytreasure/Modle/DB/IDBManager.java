package com.yan.whereismytreasure.Modle.DB;



import com.yan.whereismytreasure.Modle.Bean.ExpressInfo.Lists;
import com.yan.whereismytreasure.Modle.Bean.ExpressInfo.Result;
import com.yan.whereismytreasure.Modle.Bean.TraceExpress;
import com.yan.whereismytreasure.Modle.Bean.UserBean.MyUser;

import java.util.List;

import rx.Observable;


/**
 * interface db manager
 * Created by a7501 on 2016/6/1.
 */
public interface IDBManager {
    Observable<Boolean> setUserInfo(MyUser myUser);
    Observable<Boolean> saveExpResultList(Result result, List<Lists> list);
    Observable<Integer> doseHaveUpdate(String no,List<Lists> list);
    Observable<Boolean> traceExpress(TraceExpress traceExpress);
    Observable<Boolean> isTraceExpress(String no);
    Observable<Boolean> updateTraceTitle(String title,String no);
    Observable<List<TraceExpress>> getAllTraceExpress();

}
