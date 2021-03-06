package com.example.miwok;

import android.content.Context;
import android.media.AudioManager;
import android.media.MediaPlayer;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;

import java.util.ArrayList;


public class PhrasesFragment extends Fragment {


    private MediaPlayer mMediaPlayer;

    /* handle audio focus when sonund is plaing*/
    private AudioManager mAudioManager;

    AudioManager.OnAudioFocusChangeListener afChangeListener =
            new AudioManager.OnAudioFocusChangeListener() {
                public void onAudioFocusChange(int focusChange) {
                    if (focusChange == AudioManager.AUDIOFOCUS_LOSS) {
                        releaseMediaPlayer();
                    }
                    else if (focusChange == AudioManager.AUDIOFOCUS_LOSS_TRANSIENT ||
                            focusChange == AudioManager.AUDIOFOCUS_LOSS_TRANSIENT_CAN_DUCK) {
                        mMediaPlayer.pause();
                        mMediaPlayer.seekTo(0);
                    } else if (focusChange == AudioManager.AUDIOFOCUS_GAIN) {
                        // Your app has been granted audio focus again
                        // Raise volume to normal, restart playback if necessary
                        mMediaPlayer.start();
                    }
                }
            };


    private MediaPlayer.OnCompletionListener mCompletionListener = new MediaPlayer.OnCompletionListener() {
        @Override
        public void onCompletion(MediaPlayer mp) {
            releaseMediaPlayer();
        }
    };


    public PhrasesFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View rootView = inflater.inflate(R.layout.word_list, container, false);

        //create and set up the (@link AudioManager ) to the request audio file
        mAudioManager = (AudioManager) getActivity().getSystemService(Context.AUDIO_SERVICE);
        AudioManager.OnAudioFocusChangeListener afChangeListener = null;


        final ArrayList<word> words = new ArrayList<word>();

        words.add(new word("Where are you going?", "minto wuksus", R.raw.phrase_where_are_you_going));
        words.add(new word("What is your name?", "tinn?? oyaase'n??", R.raw.phrase_what_is_your_name));
        words.add(new word("My name is...", "oyaaset..", R.raw.phrase_my_name_is));
        words.add(new word("How are you feeling?", "mich??ks??s?", R.raw.phrase_how_are_you_feeling));
        words.add(new word("I???m feeling good.", "kuchi achit", R.raw.phrase_im_feeling_good));
        words.add(new word("Are you coming?", "????n??s'aa?", R.raw.phrase_are_you_coming));
        words.add(new word("Yes, I???m coming.", "h??????? ????n??m", R.raw.phrase_yes_im_coming));
        words.add(new word("I???m coming.", "????n??m", R.raw.phrase_im_coming));
        words.add(new word("Let???s go.", "yoowutis", R.raw.phrase_lets_go));
        words.add(new word("Come here.",    "??nni'nem", R.raw.phrase_come_here));

        WordAdapter adapter = new WordAdapter(getActivity(), words, R.color.category_numbers);

        ListView listView = (ListView) rootView.findViewById(R.id.list);

        listView.setAdapter(adapter);

        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int positon, long l) {
                word word = words.get(positon);
                //release the media player if it currently exsit because we are about to play
                // different sound file
                releaseMediaPlayer();

                // Request audio focus for playback
                int result = mAudioManager.requestAudioFocus(afChangeListener,
                        // Use the music stream.
                        AudioManager.STREAM_MUSIC,
                        // Request permanent focus.
                        AudioManager.AUDIOFOCUS_GAIN);

                if (result == AudioManager.AUDIOFOCUS_REQUEST_GRANTED) {


                    mMediaPlayer = MediaPlayer.create(getActivity(), word.getmAudioResourceId());
                    mMediaPlayer.start();
                    //set up a listener on the  media player, so we can stop the release the media player once the
                    //sound has done
                    mMediaPlayer.setOnCompletionListener(mCompletionListener);
                }
            }
        });
        return rootView;
    }

    @Override
    public void onStop() {
        super.onStop();
        //when activity stopped then release the media player resource because we won't
        //be played the sound more
        releaseMediaPlayer();
    }

    /**
     * Clean up the media player by releasing its resources.
     */
    private void releaseMediaPlayer() {
        // If the media player is not null, then it may be currently playing a sound.
        if (mMediaPlayer != null) {
            // Regardless of the current state of the media player, release its resources
            // because we no longer need it.
            mMediaPlayer.release();

            // Set the media player back to null. For our code, we've decided that
            // setting the media player to null is an easy way to tell that the media player
            // is not configured to play an audio file at the moment.
            mMediaPlayer = null;

            // Abandon audio focus when playback complete
            mAudioManager.abandonAudioFocus(afChangeListener);
        }
    }
}