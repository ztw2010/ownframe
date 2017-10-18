# ownframe
一款支持组件化单独调试，各个组件通过Route自由跳转，参数自动注入的快速开发框架

**架构图**<br>
![](https://raw.githubusercontent.com/ztw2010/ownframe/master/pic/%E6%9E%B6%E6%9E%84%E5%9B%BE.png)


**工程基础库释义**<br>
1. **:app** 主工程<br>
2. **:gradle-src** gradle脚本工程，用来<br>

&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;① Module运行时是com.android.application，主工程运行时变为com.android.library，主工程和各个独立的Module需要在各个工程目录下的build.gradle文件中需要将以前的apply plugin: 'com.android.application'替换为apply plugin: 'com.ebest.gradle.component.plugin' 用来引用gradle插件以便进行动态切换为application还是library<br>
&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;② 引入AspectJ编译器，使之识别AOP相关的代码，例如权限的动态申请<br>
3. **:componentservice** 框架基础封装&暴露模块的某些功能，例如A Module如果要加载B Module的一个Fragment时在componentservice中通过面向接口编程将其暴露出去<br>
4. **:basiclib** 基础库，用到的第三方jar包等在此处进行引用<br>
5. **:basicres** 模块共用的一些资源文件在此处添加<br>
6. **:annomationapilib** 自定义注解所暴露的一些API，供其他模块使用，例如：路由API和Intent参数自动注入API<br>
7. **:annomationlib** 自定义的一些注解，例如路由和参数注解<br>
8. **:annomationcompilelib** 自定义注解处理器，用来自动生成代码，例如自动生成XXX$$Injector和RouterRuleCreator<br>


----------

**使用注意事项**<br>
1. 每添加一个Module，首先需要在Module工程下的build.gradle文件中将以前的apply plugin: 'com.android.application'替换为apply plugin: 'com.ebest.gradle.component.plugin'插件
2. 在该Module src/main目录下新建runalone目录，在runalone目录右键New--Folder--Java Folder建立java目录，在runalone目录再右键New--Folder--Java Resource Folder建立res目录，在runalone目录再右键New--Other--Android Manifest File建立AndroidManifest.xml文件，在AndroidManifest.xml文件中配置启动Activity，这样该Module便可以run了<br>
3. 在新建的Module工程中建一个继承自com.ebest.frame.annomationapilib.route.ModuleRouteConfig文件，例如<br>
 @RouteConfig(baseUrl = "loginmodule://", pack = "com.ebest.frame.loginmodule")<br>
public class XXXRouteConfig extends ModuleRouteConfig{
}<br>
baseUrl：指定该Module的URI前缀<br>
pack：指定该Module由自定义注解处理器生成的RouterRuleCreator.java所在的包名<br>
4. 在新建的Module工程中建一个继承自com.ebest.frame.commnetservicve.applike.IApplicationLike的文件B Class，在B的onCreate方法中将该Module生成的路由表加入到中央路由中，以便其他模块能够跳转到该Module，在编译成class时会由gradle脚本在主工程的自定义Application类中的onCreate方法会自动调用B Class的oncreate方法<br>
5. 如果新建的Module需要用到AOP，那么需要将对应的gradle插件引入到builde.gradle中，例如apply plugin: AspectjPlugin<br>
6. 在工程目录下的gradle.properties文件中指定主工程名字例如：mainmodulename=app。<br>在主工程的gradle.properties文件中添加<br>isRunAlone=true<br>
debugComponent=loginmodule<br>
compileComponent=com.ebest.module.loginmodule:loginmodule<br>

isRunAlone=true：工程变为com.android.application<br>
debugComponent主工程以debug运行时所依赖的工程为loginmodule<br>
compileComponent 主工程以release运行时所依赖的aar为loginmodule-release.aar<br>
在Module工程中gradle.properties文件中添加isRunAlone=true，该Module可以独立运行，改为false并运行assembleRelease即发布aar文件到componentrelease目录中，只有在发布aar时才需要修改isRunAlone=false其他任何情况下都不需要修改，保持为true，在运行主工程时gradle插件会自动修改其为libarary

