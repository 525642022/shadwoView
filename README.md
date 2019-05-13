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

