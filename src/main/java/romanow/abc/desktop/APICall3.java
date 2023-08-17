package romanow.abc.desktop;

import retrofit2.Call;
import retrofit2.Response;
import romanow.abc.core.UniException;
import romanow.abc.core.Utils;
import romanow.abc.core.constants.ValuesBase;

import java.io.IOException;

public abstract class APICall3<T> {
    public abstract Call<T> apiFun();
    public abstract void onSucess(T oo);
    public APICall3(){}
    public String call(MainBaseFrame base){
        try {
            long tt = System.currentTimeMillis();
            Response<T> res = apiFun().execute();
            if (!res.isSuccessful()){
                if (res.code()== ValuesBase.HTTPAuthorization){
                    if (base!=null)
                        base.logOff();
                    return "Сеанс закрыт " + Utils.httpError(res);
                    }
                return "Ошибка " + res.message()+" ("+res.code()+") "+res.errorBody().string();
                }
            else{
                onSucess(res.body());
                return null;
                }
        } catch (Exception ex) {
            return Utils.createFatalMessage(ex);
            }
        }
    }

