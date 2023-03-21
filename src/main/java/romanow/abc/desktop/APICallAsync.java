package romanow.abc.desktop;

import retrofit2.Call;
import retrofit2.Response;
import romanow.abc.core.Utils;
import romanow.abc.core.constants.ValuesBase;

import javax.swing.*;
import java.io.IOException;

    public abstract class APICallAsync<T> {
        public abstract Call<T> apiFun();
        public abstract void onSucess(T oo);
        public APICallAsync(final JButton busyButton, final MainBaseFrame base){
            busyButton.setVisible(true);
            new Thread(new Runnable() {
                @Override
                public void run() {
                    try {
                        long tt = System.currentTimeMillis();
                        final Response<T> res = apiFun().execute();
                        java.awt.EventQueue.invokeLater(new Runnable() {
                            public void run() {
                                busyButton.setVisible(false);
                                if (!res.isSuccessful()){
                                    if (res.code()== ValuesBase.HTTPAuthorization){
                                        System.out.println("Сеанс закрыт " + Utils.httpError(res));
                                        if (base!=null)
                                            base.logOff();
                                        return;
                                    }
                                    try {
                                        System.out.println("Ошибка " + res.message()+" ("+res.code()+") "+res.errorBody().string());
                                    } catch (IOException e) {}
                                    return;
                                }
                                else{
                                    System.out.println("time="+(System.currentTimeMillis()-tt)+" мс");
                                    onSucess(res.body());
                                }
                            }
                        });
                    } catch (Exception ex) {
                        java.awt.EventQueue.invokeLater(new Runnable() {
                            public void run() {
                                busyButton.setVisible(false);
                                Utils.printFatalMessage(ex);
                            }
                        });
                    }
                }
            }).start();
        }

    }

