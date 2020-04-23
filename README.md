# FactoryBox

[![](https://img.shields.io/badge/maven-v1.0.3-brightgreen.svg)](https://bintray.com/owant/HappyCode/factorybox)

```
implementation 'com.owant.factorybox:factorybox:1.0.3'
annotationProcessor 'com.google.dagger:dagger-compiler:2.2'
```

使用google的auto-service和AbstractProcessor进行代码生成。看了不少讲解AbstractProcessor的教程，后来我一直在需要找其应用的实例。后来在项目中的一个配置文件经常改动想到了自动生成代码，于是考虑实践一下

## 例子
1、需要实现的接口
```
public interface Event {

    void onEvent();
}


```
2、具体的实现接口的类
```
@FactoryBox(key = "destroy", product = Event.class)
public class DestroyEvent implements Event {

    @Override
    public void onEvent() {

    }
}


@FactoryBox(key = "resume", product = Event.class)
public class ResumeEvent implements Event {

    @Override
    public void onEvent() {

    }
}

```

3、自动生成部分
```
package com.owant.createcode.testcode;

import java.lang.Exception;
import java.lang.String;
import java.util.HashMap;

public final class EventFactory {

  private final HashMap<String, Event> products;

  public EventFactory() {
    this.products = new HashMap<String, Event>();
  }

  public Event create(String key) throws Exception {
    Event cache = products.get(key);
    if (cache != null) {
      return cache;
    }
    if ("sub_event".equals(key)) {
      products.put(key, new SubEvent());
    } else if ("create".equals(key)) {
      products.put(key, new CreateEvent());
    } else if ("destroy".equals(key)) {
      products.put(key, new DestroyEvent());
    } else if ("resume".equals(key)) {
      products.put(key, new ResumeEvent());
    } else {
      throw new Exception(String.format("没有到key=%s对应的实现", key));
    }
    return products.get(key);
  }
}

```


## 好处
实现工厂模式的拓展，不关心工厂的实现，工厂由代码进行生成。减少了更改。


## 版本更新

* 1.0.1 实现一个简易的工厂
* 1.0.2 优化代码，实现工厂缓存
* 1.0.3 实现支持构造函数的工厂
