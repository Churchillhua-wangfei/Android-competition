## Volley

源码解析：https://blog.csdn.net/qq_16054639/article/details/79936120

**官网**：https://android.googlesource.com/platform/frameworks/volley
**功能**
	1、普通数据、JSON、图片的异步加载
	2、网络请求优先级处理
	3、自带硬盘缓存（普通数据、图片、JSON），另外在加载图片时通过ImageLoader还可加入LruCache
	4、取消请求
	5、与Activity生命周期联动（Activity退出时同时取消所有的请求）
**特点**
	通信更快,更简单
	支持网络请求的排序,优先级处理
	支持网络请求的缓存
	多级别的取消请求
	扩展性强
	大量的频繁的小数据量传输
**应用**：高并发，频繁通信，单次请求数据少
**下载**
方法一：git clone https://android.googlesource.com/platform/frameworks/volley导入到工程然后到导出jar包
方法二：下载：http://www.kwstu.com/ResourcesView/kwstu_201441183330928
		下载：http://download.csdn.net/detail/u010049692/7813127
		下载：https://download.csdn.net/download/u010687392/8996139
**使用**
	1、创建RequestQueue
	2、创建Request对象
	3、添加Request对象到RequestQueue中
**请求类型**
	StringRequest				返回字符串数据
	JsonObjectRequest		返回JSONArray数据
	JsonArrayRequest		  返回JSONObject数据
	ImageRequest			 	返回Bitmap类型数据

### GET请求

```java
//生成请求队列
RequestQueue mQueue = Volley.newRequestQueue(context);  
//用于key-value响应的请求
StringRequest request = new StringRequest("http://www.baidu.com",  
    new Response.Listener<String>() {  
        @Override  
        public void onResponse(String response) {}}, 
    new Response.ErrorListener() {  
        @Override  
        public void onErrorResponse(VolleyError error) {}}
); 
//用于json回调请求
String path = "http://m.weather.com.cn/data/101010100.html"
JsonObjectRequest jsonObjectRequest = new 、JsonObjectRequest(Method.POST，path,  
	new Response.Listener<JSONObject>() {  
		@Override  
		public void onResponse(JSONObject response) {}}, 
     new Response.ErrorListener() {  
		@Override  
		public void onErrorResponse(VolleyError error) {}}
);  
//用于bitmap回调请求
ImageRequest imageRequest = new ImageRequest(url, 
	new Response.Listener<Bitmap>() {
		@Override
		public void onResponse(Bitmap bitmap) {}}, 
	0, 0, Bitmap.Config.RGB_565, 
	new Response.ErrorListener() {
		@Override
		public void onErrorResponse(VolleyError volleyError) {}}
);
//将请求添加到队列中
mQueue.add(request);
//开始请求
mQueue.start();
//取消所有队列
mQueue.cancelAll(context);
```

```java
//自定义类实现ImageLoader.ImageCache。限定最大缓存，返回图片的对应宽高
public class BitmapCache implements ImageLoader.ImageCache{
    private LruCache<String,Bitmap> mCache;
    public BitmapCache() {
        int maxSize = 10* 1024 *1024;	//10M
        mCache = new LruCache<String,Bitmap>(maxSize){
            @Override
            protected int sizeOf(String key, Bitmap bitmap) {
                return bitmap.getRowBytes() * bitmap.getHeight();
            }
        };
    }
    @Override
    public Bitmap getBitmap(String url) {
        return mCache.get(url);
    }
    @Override
    public void putBitmap(String url, Bitmap bitmap) {
        mCache.put(url,bitmap);
    }
}
//使用带LruCache缓存的加载方式(ImageView+ImageLoader+ImageCache)
RequestQueue mQueue = Volley.newRequestQueue(context);
ImageLoader imageLoader = new ImageLoader(requestQueue, new BitmapCache());//带缓存
ImageLoader.ImageListener imageLister = imageLoader.getImageListener(mImageView, R.mipmap.ic_launcher, R.mipmap.ic_launcher);
//参数：指定控件，默认图片，错误图片
imageLoader.get(url, imageLister,0,0);

//使用NetworkImageView+ImageLoader+ImageCache
//NetworkImageView扩展了ImageView，多了setImageUrl(),setDefaultImageResId(),setErrorImageResId
<com.android.volley.toolbox.NetworkImageView
        android:id="@+id/net_iamge"
        android:layout_width="match_parent"
        android:layout_height="warp_content" />
            
RequestQueue mRequestQueue = Volley.newRequestQueue(getApplicationContext());
ImageLoader mImageLoader = new ImageLoader(mRequestQueue,new BitmapCache());
mNetworkImageView.setDefaultImageResId(R.mipmap.ic_launcher);	//默认图片
mNetworkImageView.setErrorImageResId(R.mipmap.ic_launcher);		//出错时图片
mNetworkImageView.setImageUrl(url,mImageLoader);
```

### POST请求

```java
//生成请求队列
RequestQueue mQueue = Volley.newRequestQueue(context);  
//用于key-value响应的请求
StringRequest stringRequest = new StringRequest(Method.POST,url,
    new Response.Listener<String>() {  
        @Override  
        public void onResponse(String response) {}}, 
    new Response.ErrorListener() {  
        @Override  
        public void onErrorResponse(VolleyError error) {}}
){
    @Override  	//重写getParams方法，添加请求头
    protected Map<String, String> getParams() throws AuthFailureError {  
        Map<String, String> map = new HashMap<String, String>();  
        map.put("params1", "value1");  
        map.put("params2", "value2");  
        return map;  
    }  
}; 
//将请求添加到队列中
mQueue.add(stringRequest);
//开始请求
mQueue.start();
//取消所有队列
mQueue.cancelAll(context);
```

### 原理

​	通过一个请求队列(RequestQueue)来维护所有请求，我们新创建一个请求(request)后通过RequestQueue.add()将请求添加置请求队列中，然后调用RequestQueue.start()执行请求队列中的方法 

![](Volley%E5%8E%9F%E7%90%86%E5%9B%BE.png)

## 