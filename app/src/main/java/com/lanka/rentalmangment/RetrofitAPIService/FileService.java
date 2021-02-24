package com.lanka.rentalmangment.RetrofitAPIService;

import com.lanka.rentalmangment.RetrofitClient.Connection;
import com.lanka.rentalmangment.RetrofitInterface.FileApi;

public class FileService {
    FileApi fileApi;
    Connection connection;

    public FileService() {
        this.fileApi = connection.getRetrofitClientInstance().create(FileApi.class);
    }


//    public void getListFiles(String ?, ResponseCallback callback) {
//        Call<List<ResponseFile>> call = fileApi.getListFiles(?);
//        call.enqueue(new CustomizeCallback<List<ResponseFile>>(callback));
//    }
//    public void getFile(String ?, ResponseCallback callback)
//    {
//        Call<?> call= fileApi.getFile(String,?);
//        call.enqueue(new CustomizeCallback<?>(callback));
//    }
//    public void uploadFile(String ?, ResponseCallback callback)
//    {
//        Call<?> call= fileApi.getFile(String,?);
//        call.enqueue(new CustomizeCallback<?>(callback));
//    }
}
