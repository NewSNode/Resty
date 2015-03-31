package cn.dreampie.resource;

import cn.dreampie.common.http.HttpStatus;
import cn.dreampie.common.http.UploadedFile;
import cn.dreampie.common.http.WebResult;
import cn.dreampie.common.util.Maper;
import cn.dreampie.resource.user.model.User;
import cn.dreampie.route.core.annotation.*;
import cn.dreampie.security.Principal;
import cn.dreampie.security.Subject;

import java.io.File;
import java.util.Date;
import java.util.Map;

/**
 * Created by wangrenhui on 15/1/10.
 */
@API("/tests")
public class TestResource extends ApiResource {


  @POST(value = "/login")
  public Principal login(String username, String password) {
    Subject.login(username, password);
    return Subject.getPrincipal();
  }

  @GET
  public WebResult get() {
    Subject.login("userq", "123");
    //如果需要返回请求状态  new WebResult
    return new WebResult(HttpStatus.OK, Maper.of("a", "1", "b", "2"));
  }

  @POST("/tests")
  public Map post(Map<String, String> test) {
    return test;
  }

  @PUT("/:b")
  public Map put(String b) {
    Map<String, String> map = Maper.of("a", "1", "b", "2");
    map.put("b", b);
    return map;
  }

  @DELETE("/:key")
  public Map delete(String key) {
    Map<String, String> map = Maper.of("a", "1", "b", "2");
    map.remove(key);
    return map;
  }

  //上传文件
  @POST("/:filename")
  public UploadedFile upload(String filename) {
    //如果上传文件的同时 有参数
    UploadedFile uploadedFile = getFile(filename);
    System.out.println(getParameter("des"));
    return uploadedFile;
  }

  //下载文件
  @GET("/file")
  public File download() {
    return new File(getRequest().getRealPath("/") + "upload/resty.jar");
  }


  @GET("/boom")
  public void test() {
    new User().set("username", "test" + Thread.currentThread().getName()).set("providername", "test").set("created_at", new Date())
        .set("password", "123456").set("sid", "1").save();
  }
}
