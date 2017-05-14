package com.unicornheight.popularmovie2.modules.home.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.unicornheight.popularmovie2.R;
import com.unicornheight.popularmovie2.helper.ImageHandler;
import com.unicornheight.popularmovie2.mvp.model.Movie;

import java.util.ArrayList;
import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;

/**
 * Created by deboajagbe on 5/10/17.
 */

public class MovieAdapter extends RecyclerView.Adapter<MovieAdapter.Holder> {

    private LayoutInflater mLayoutInflater;
    private List<Movie> mMovieList = new ArrayList<>();

    public MovieAdapter(LayoutInflater inflater) {
        mLayoutInflater = inflater;
    }

    @Override
    public Holder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = mLayoutInflater.inflate(R.layout.movie_list, parent, false);
        return new Holder(view);
    }

    @Override
    public void onBindViewHolder(Holder holder, int position) {
        holder.bind(mMovieList.get(position));
    }

    @Override
    public int getItemCount() {
        return mMovieList.size();
    }

    public void addMovies(List<Movie> movies) {
        mMovieList.addAll(movies);
        notifyDataSetChanged();
    }

    public void clearMovies() {
        mMovieList.clear();
        notifyDataSetChanged();
    }

    public class Holder extends RecyclerView.ViewHolder implements View.OnClickListener {

        @Bind(R.id.movie_image) protected ImageView mMoviePoster;

        private Context mContext;
        private Movie mMovie;

        public Holder(View itemView) {
            super(itemView);
            itemView.setOnClickListener(this);
            mContext = itemView.getContext();
            ButterKnife.bind(this, itemView);
        }

        public void bind(Movie movie) {
            mMovie = movie;
            Glide.with(mContext).load("http://image.tmdb.org/t/p/w500" + movie.getPoster_path())
                    .diskCacheStrategy(DiskCacheStrategy.SOURCE)
                    .placeholder(R.drawable.empty)
                    .error(R.drawable.empty)
                    .into(new ImageHandler(mMoviePoster));
        }

        @Override
        public void onClick(View v) {
            if (mMovieClickListener != null) {
                mMovieClickListener.onClick(mMoviePoster, mMovie, getAdapterPosition());
            }
        }
    }

    public void setMovieClickListener(OnMovieClickListener listener) {
        mMovieClickListener = listener;
    }

    private OnMovieClickListener mMovieClickListener;

    public interface OnMovieClickListener {

        void onClick(View v, Movie movie, int position);
    }
}
