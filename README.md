# Permission

Permission为权限请求框架，基于aspectj实现，使用注解即可请求权限,简单方便。
NeedPermission支持在所有的方法中使用和activity的类上使用,提供了权限前操作，权限后的处理(权限拒绝或不再提醒)等功能。
# 使用
### 使用到的类:
  1. **Permission**:用于初始化的类
  1. **@NeedPermission**:请求权限的注解,可以作用于任何类的方法和activity类上,拥有参数:
      - value:要请求的权限
      - requestCode:请求码
      - isAllowExecution:当权限被拒绝是否继续执行
      - requestBefore:请求前调用的方法,和@PermissionBefore配合使用,会调用带有相同参数的被PermissionBefore注解的方法
      - permissionCanceled:请求被取消的方法,和@PermissionCanceled配合使用,会调用带有相同参数的被PermissionCanceled注解的方法
      - permissionDenied:请求被拒绝的方法,和@permissionDenied配合使用,会调用带有相同参数的被permissionDenied注解的方法
  1. **@PermissionBefore**:请求权限前的操作，可以在请求权限的本类中和配置类中使用，被注解的方法的参数只能是PermissionBeforeBean
,与NeedPermission联合使用,通过requestBefore匹配
  1. **@PermissionCanceled**:权限被取消(用户点击禁止权限)时调用的方法,可以在请求权限的本类中和配置类中使用
，被注解的方法的参数只能是PermissionCanceledBean,与Neddpermission联合使用,通过permissionCanceled匹配
  1. **@PermissionDenied**:权限被取消(用户勾选禁止后不再提示并点击禁止权限)时调用的方法,可以在请求权限的本类中和配置类中使用
，被注解的方法的参数只能是PermissionDeniedBean,与Neddpermission联合使用,通过permissionDenied匹配

### 使用方式
  在你的Application的onCreate方法中使用Permission.getInstance().init()初始化,然后在需要权限的方法
  PermissionConfig该类用于配置统一的权限前置操作、取消操作、拒绝操作的公共方法。
  ~~~
  public void onCreate() {
        super.onCreate();
        Permission.getInstance().init(getApplicationContext(),new PermissionConfig());//PermissionConfig如果不用可设置为null
    }
  ~~~
  
方法中使用注解时:
~~~
@NeedPermission({Manifest.permission.CAMERA,Manifest.permission.WRITE_EXTERNAL_STORAGE,isAllowExecution = true})
    private void onClickContacts() {
        //isAllowExecution:false时，无法进入该方法 true:可以在拒绝权限后依然进入
    }
~~~
以上就是简单使用方式,一个注释搞定,是不是很简单,如果有更多需求,请继续往下看

activity中使用注解时:
~~~
@NeedPermission(value = {Manifest.permission.CAMERA, Manifest.permission.WRITE_EXTERNAL_STORAGE}, isAllowExecution = true)
public class CameraActivity extends Activity implements PermissionListener {

    @Override
    public void onPermissionGranted() {
       
    }

    @Override
    public void onPermissionCanceled(PermissionCanceledBean bean) {

    }

    @Override
    public void onPermissionDenied(PermissionDeniedBean bean) {

    }

    @PermissionBefore
    public void before(final PermissionBeforeBean beforeBean){
        
    }

｝
~~~

PermissionConfig使用如下:
~~~
public class PermissionConfig   {

    @PermissionBefore(Manifest.permission.CAMERA)//该注解注解的方法参数只能是PermissionBeforeBean
    public void before(final PermissionBeforeBean beforeBean){
       
    }

    @PermissionCanceled(Manifest.permission.CAMERA)//该注解注解的方法参数只能是PermissionCanceledBean
    public void cancel(final PermissionCanceledBean canceledBean){
        
    }

    @PermissionDenied(Manifest.permission.CAMERA)//该注解注解的方法参数只能是PermissionDeniedBean
    public void denied(final PermissionDeniedBean deniedBean){
        
    }

}
~~~
当然,如果你还有什么特殊需要也可以直接用工具类PermissionUtils
~~~
PermissionUtils.requestPermissions(context, permissions, requestCode, new PermissionListener() {
            @Override
            public void onPermissionGranted() {
               
            }

            @Override
            public void onPermissionCanceled(PermissionCanceledBean bean) {
                
            }

            @Override
            public void onPermissionDenied(PermissionDeniedBean bean) {
                
            }
        });
~~~

# 依赖
在你的根目录的build.gradle中添加配置[com.github.gh-zhang-quan:aspectj_plugin:1.0.1](https://github.com/gh-zhang-quan/aspectj_plugin)如下:
~~~
buildscript {
 
    repositories {
        maven { url "https://jitpack.io" }
    }
    dependencies {
        classpath 'com.android.tools.build:gradle:3.0.1'
        classpath 'com.github.gh-zhang-quan:aspectj_plugin:1.0.1'
    }
}
allprojects {
    repositories {
        maven { url "https://jitpack.io" }
    }
}

~~~
然后在你的app的build.gradle中使用插件和依赖
~~~
apply plugin: 'com.android.application'
apply plugin: 'aspectj-plugin'
dependencies {
    implementation 'com.github.gh-zhang-quan:permission:1.0.0'
}
~~~

添加忽略文件
~~~
-keep class com.zhang.permission.annotation.* {*;}
-keepclassmembers class com.zhang.permission.aspect.* {*;}
-keep @com.zhang.annotation.NeedPermission class * {*;}
-keep class * {
    @com.zhang.permission.annotation.NeedPermission <fields>;
}
-keepclassmembers class * {
    @com.zhang.permission.annotation.NeedPermission <methods>;
}
~~~
如果好用请点个star,有问题请联系zhang_quan_888@163.com
