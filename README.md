# PagePoint
===

##ViewPager滑动时分页的小圆点，使用这个控件可以免去控制小圆点变化的繁琐；
-----

### 演示
![image](https://github.com/llj19900605/PagePoint/raw/master/screenshots/demo.gif)

##代码参考
####在xml布局中引入'PagerPointView',然后设置一些属性：
```xml
<com.jerry.pagepoint.PagePointView
        android:layout_width="wrap_content"
        android:layout_height="wrap_content"
        android:layout_alignParentBottom="true"
        android:layout_marginBottom="50dp"
        android:layout_centerHorizontal="true"
        app:ppv_pointRadius="5dp"
        app:ppv_hasBorder="true"
        app:ppv_borderWidth="1dp"
        app:ppv_focusPointColor="@android:color/black"
        app:ppv_unfocusPointColor="@android:color/white"
        android:id="@+id/point"
        />
```
####在代码中给PagePointView设置ViewPager就行了
```java
        ViewPager viewPager = (ViewPager) findViewById(R.id.viewPager);
        PagePointView point = (PagePointView) findViewById(R.id.point);
        viewPager.setAdapter(adapter);
        point.setViewPager(viewPager);
```
###注意：在给PagePointView设置ViewPager之前先给ViewPager设置Adapter
