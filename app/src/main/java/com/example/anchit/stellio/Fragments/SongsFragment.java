package com.example.anchit.stellio.Fragments;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Toast;

import com.example.anchit.stellio.PlayerActivity;
import com.example.anchit.stellio.R;
import com.example.anchit.stellio.SongsList;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Map;
import java.util.TreeMap;

/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link SongsFragment.OnFragmentInteractionListener} interface
 * to handle interaction events.
 * Use the {@link SongsFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class SongsFragment extends Fragment {

    private ListView songsList;

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    private OnFragmentInteractionListener mListener;

    public SongsFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment SongsFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static SongsFragment newInstance(String param1, String param2) {
        SongsFragment fragment = new SongsFragment();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment

        View view = inflater.inflate(R.layout.fragment_songs, container, false);

        listSongs(view);

        return view;
    }

    public void listSongs(View view)   {
        SongsList list = new SongsList();
        final ArrayList<String> sortedSongs = new ArrayList<>();
        sortedSongs.addAll(list.getSongs(getContext()));
        Collections.sort(sortedSongs);
        songsList = view.findViewById(R.id.songsList);
        ArrayAdapter<String> adapter = new ArrayAdapter<>(getContext(),android.R.layout.simple_list_item_1,sortedSongs);
        songsList.setAdapter(adapter);

        final Map<String,String> songPathArray = new TreeMap<>(list.getSortedTitlePathTreeMap(getContext()));
        final Map<String,String> TitleAlbumTreeMap = new TreeMap<>(list.getSortedTitleAlbumTreeMap(getContext()));

        songsList.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                if (songPathArray.containsKey(sortedSongs.get(position))) {
                    Intent intent = new Intent(getContext(), PlayerActivity.class);

                    Object path = songPathArray.values().toArray()[position];
                    intent.putExtra("path", String.valueOf(path));
                    intent.putExtra("songName",songsList.getItemAtPosition(position).toString());
                    intent.putExtra("songAlbum",TitleAlbumTreeMap.get(songsList.getItemAtPosition(position)));
                    startActivity(intent);

                }   else {
                    Toast.makeText(getContext(), "Song doesn't Exists..", Toast.LENGTH_SHORT).show();
                }
            }
        });

    }

    // TODO: Rename method, update argument and hook method into UI event
    public void onButtonPressed(Uri uri) {
        if (mListener != null) {
            mListener.onFragmentInteraction(uri);
        }
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        if (context instanceof OnFragmentInteractionListener) {
            mListener = (OnFragmentInteractionListener) context;
        } else {
            throw new RuntimeException(context.toString()
                    + " must implement OnFragmentInteractionListener");
        }
    }

    @Override
    public void onDetach() {
        super.onDetach();
        mListener = null;
    }

    /**
     * This interface must be implemented by activities that contain this
     * fragment to allow an interaction in this fragment to be communicated
     * to the activity and potentially other fragments contained in that
     * activity.
     * <p>
     * See the Android Training lesson <a href=
     * "http://developer.android.com/training/basics/fragments/communicating.html"
     * >Communicating with Other Fragments</a> for more information.
     */
    public interface OnFragmentInteractionListener {
        // TODO: Update argument type and name
        void onFragmentInteraction(Uri uri);
    }
}
