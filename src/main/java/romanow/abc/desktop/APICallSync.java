package romanow.abc.desktop;

import retrofit2.Call;
import retrofit2.Response;
import romanow.abc.core.Utils;
import romanow.abc.core.constants.ValuesBase;
import romanow.abc.core.utils.Pair;

public abstract class APICallSync<T> {
    public abstract Call<T> apiFun();
    public APICallSync(){}
    public Pair<String,T> call(){
        try {
            long tt = System.currentTimeMillis();
            Response<T> res = apiFun().execute();
            if (!res.isSuccessful()){
                if (res.code()== ValuesBase.HTTPAuthorization){
                    return new Pair("Сеанс закрыт " + Utils.httpError(res),null);
                    }
                return new Pair<>("Ошибка " + res.message()+" ("+res.code()+") "+res.errorBody().string(),null);
                }
            else{
                return new Pair<>(null,res.body());
                }
        } catch (Exception ex) {
            return new Pair(Utils.createFatalMessage(ex),null);
            }
        }
    }

