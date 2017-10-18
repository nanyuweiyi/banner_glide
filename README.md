# banner_glide

1、在project的build.gradle：maven { url 'https://jitpack.io' }

2、在app的build.gradle：compile 'com.github.nanyuweiyi:banner_glide:1.0'

3、demo:

List<String> imageUrlList = new ArrayList<>();
    List<String> imageLinkList = new ArrayList<>();

    private RollPagerView bannerView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        imageUrlList.add("http://img.my.csdn.net/uploads/201508/05/1438760757_3588.jpg");
        imageUrlList.add("https://timgsa.baidu.com/timg?image&quality=80&size=b9999_10000&sec=1488801109414&di=b3ea62058e9ba4afb0d75a10a8917d89&imgtype=0&src=http%3A%2F%2Fa0.att.hudong.com%2F15%2F52%2F01300001017421128637528950010.jpg");

        imageLinkList.add("http://www.baidu.com");
        imageLinkList.add("http://www.baidu.com");

        initRollViewPager();
    }

    private void initRollViewPager() {
        bannerView = (RollPagerView) findViewById(R.id.banner_view);
        bannerView.setOnItemClickListener(new OnItemClickListener() {
            @Override
            public void onItemClick(int position) {
                Toast.makeText(MainActivity.this, "点了第"+position+"个", Toast.LENGTH_SHORT).show();
            }
        });
        bannerView.setAdapter(new LoopAdapter(bannerView));
        bannerView.setHintPadding(35, 0, 35, 15);
        bannerView.setClickable(true);
    }

    private class LoopAdapter extends LoopPagerAdapter {

        public LoopAdapter(RollPagerView viewPager) {
            super(viewPager);
        }

        @Override
        public View getView(ViewGroup container, int position) {
            ImageView view = new ImageView(MainActivity.this);
            view.setScaleType(ImageView.ScaleType.FIT_XY);
            view.setLayoutParams(new ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT));

            ImageUtilGlide.displayImage(view, imageUrlList.get(position));

            return view;
        }

        @Override
        public int getRealCount() {
            return imageUrlList.size();
        }
    }
