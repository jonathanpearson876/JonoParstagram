package com.jonathan.jonoparstagram.fragments

import android.util.Log
import com.jonathan.jonoparstagram.Post
import com.parse.FindCallback
import com.parse.ParseException
import com.parse.ParseQuery
import com.parse.ParseUser

class ProfileFragment : FeedFragment() {

    override fun queryPosts(){
        // Specify which class to query
        val query: ParseQuery<Post> = ParseQuery.getQuery(Post::class.java)
        //Find all post objects
        query.include(Post.KEY_USER)
        query.whereEqualTo(Post.KEY_USER, ParseUser.getCurrentUser())
        query.addDescendingOrder("createdAt")

        //Only return most recent 20 posts

        query.findInBackground(object : FindCallback<Post> {
            override fun done(posts: MutableList<Post>?, e: ParseException?) {
                if(e !=null){
                    Log.e(TAG, "Error fetching posts")
                } else {
                    if(posts != null){
                        for(post in posts){
                            Log.i(TAG,"Post:" + post.getDescription() +
                                    post.getUser()?.username
                            )
                        }
                        adapter.clear()
                        allPosts.addAll(posts)
                        adapter.notifyDataSetChanged()
                        swipeContainer.setRefreshing(false)
                    }
                }
            }

        })
    }
}