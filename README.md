这里面包括两个两个自定义View 和一个自定义ViewGrop
第一个自定义View  


<com.example.shadowlibrary.MaskTextView
            android:layout_width="match_parent"
            android:layout_height="60dp"
            android:layout_gravity="center_horizontal"
            android:layout_marginStart="20dp"
            android:layout_marginTop="20dp"
            android:layout_marginEnd="20dp"
            app:shadowText="自定义View实现阴影" />
            
            
使用 BlurMaskFilter实现
可以较好的实现一个扩散阴影的效果，可以支持阴影使用渐变色，也可以是图片颜色的延伸

实现效果

![实现效果](https://github.com/525642022/shadwoView/blob/master/1.png)

第二个自定义Views使用setShadowLayer实现



  <com.example.shadowlibrary.ShadowTextView
            android:layout_width="match_parent"
            android:layout_height="60dp"
            android:layout_gravity="center_horizontal"
            android:layout_marginStart="20dp"
            android:layout_marginTop="20dp"
            android:layout_marginEnd="20dp"
            app:shadowText="自定义View实现阴影"
            app:shadowTextRadius="10dp" />
            
            
            
            
 实现效果
 
 ![实现效果](https://github.com/525642022/shadwoView/blob/master/2.png)
 
 
 自定ViewGrop使用 第二个自定义Views使用setShadowLayer实现实现
 
     <com.example.shadowlibrary.MaskViewGroup
            android:layout_width="match_parent"
            android:layout_height="150dp"
            android:layout_gravity="center_horizontal"
            android:layout_marginStart="10dp"
            android:layout_marginTop="10dp"
            android:layout_marginEnd="10dp"
            android:layout_marginBottom="20dp"
            app:containerCornerRadius="27dp"
            app:containerDeltaLength="10dp"
            app:containerShadowColor="@color/colorE9E9E9"
            app:containerShadowRadius="6dp"
            app:deltaX="0dp"
            app:deltaY="-3dp"
            app:enable="true">

            <RelativeLayout
                android:layout_width="match_parent"
                android:layout_height="match_parent"
                android:background="@drawable/shape_27_white"
                android:gravity="center" />
        </com.example.shadowlibrary.MaskViewGroup>
        
        
  实现效果
  
  
 ![实现效果](https://github.com/525642022/shadwoView/blob/master/3.png)
 
 博客地址：https://www.jianshu.com/p/9b5d111aa306
 
 
 
            


