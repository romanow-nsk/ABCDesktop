package romanow.abc.desktop;

import romanow.abc.core.API.RestAPIBase;
import romanow.abc.core.UniException;
import romanow.abc.core.Utils;
import retrofit2.Call;
import retrofit2.Response;
import romanow.abc.core.constants.ValuesBase;

import java.io.IOException;

public abstract class APICallServer<T> {
    public abstract Call<T> apiFun();
    public APICallServer(){}
    public T call(RestAPIBase service)throws UniException {
        String mes;
        Response<T> res;
        long tt;
        try {
            tt = System.currentTimeMillis();
            res = apiFun().execute();
            } catch (Exception ex) {
                throw UniException.bug(ex);
                }
            if (!res.isSuccessful()){
                if (res.code()== ValuesBase.HTTPAuthorization){
                    mes =  "Сеанс закрыт " + Utils.httpError(res);
                    System.out.println(mes);
                    throw UniException.io(mes);
                    }
                try {
                    mes = "Ошибка " + res.message() + " (" + res.code() + ") " + res.errorBody().string();
                    } catch (IOException ex){ mes=ex.toString(); }
                System.out.println(mes);
                throw UniException.io(mes);
                }
            //System.out.println("time="+(System.currentTimeMillis()-tt)+" мс");
            return res.body();
            }
    }

