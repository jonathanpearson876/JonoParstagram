package com.jonathan.jonoparstagram.fragments

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout
import com.jonathan.jonoparstagram.MainActivity
import com.jonathan.jonoparstagram.Post
import com.jonathan.jonoparstagram.PostAdapter
import com.jonathan.jonoparstagram.R
import com.parse.FindCallback
import com.parse.ParseException
import com.parse.ParseQuery

open class FeedFragment : Fragment() {

    lateinit var postsRecyclerView: RecyclerView

    lateinit var adapter: PostAdapter

    lateinit var swipeContainer: SwipeRefreshLayout

    var allPosts: MutableList<Post> = mutableListOf()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_feed, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        //We will set up views here

        postsRecyclerView = view.findViewById(R.id.postRecyclerView)
        swipeContainer = view.findViewById(R.id.swipeContainer)
        swipeContainer.setOnRefreshListener {
            // Your code to refresh the list here.
            // Make sure you call swipeContainer.setRefreshing(false)
            // once the network request has completed successfully.
            queryPosts()
        }

        // Configure the refreshing colors
        swipeContainer.setColorSchemeResources(android.R.color.holo_blue_bright,
            android.R.color.holo_green_light,
            android.R.color.holo_orange_light,
            android.R.color.holo_red_light);

        adapter = PostAdapter(requireContext(), allPosts)
        postsRecyclerView.adapter = adapter

        postsRecyclerView.layoutManager = LinearLayoutManager(requireContext())

        queryPosts()

    }

    //Query for all posts in our server
    open fun queryPosts(){

        // Specify which class to query
        val query: ParseQuery<Post> = ParseQuery.getQuery(Post::class.java)
        //Find all post objects
        query.include(Post.KEY_USER)
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

    companion object{
        const val TAG = "FeedFragment"
    }
}