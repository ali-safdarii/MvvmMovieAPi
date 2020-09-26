package com.example.moviemvvm.slider

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.viewpager.widget.PagerAdapter
import com.bumptech.glide.Glide
import com.example.moviemvvm.R

class SliderPagerAdapter(private val context: Context, private val mList: ArrayList<Slider>) :
    PagerAdapter() {


    override fun isViewFromObject(view: View, `object`: Any): Boolean {
        return view == `object`
    }

    override fun getCount(): Int {
        return mList.size
    }


    override fun destroyItem(container: ViewGroup, position: Int, `object`: Any) {
        container.removeView(`object` as View?)
    }

    override fun instantiateItem(container: ViewGroup, position: Int): Any {
        val inflater: LayoutInflater =
            context.getSystemService(Context.LAYOUT_INFLATER_SERVICE) as LayoutInflater

        val view:View=inflater.inflate(R.layout.slide_item,null)

        val imageView:ImageView=view.findViewById(R.id.slide_img)
        val textView:TextView=view.findViewById(R.id.slide_title)

        Glide.with(context).load(mList[position].image).into(imageView)

        textView.text=mList[position].title


        container.addView(view)

        return view

    }
}