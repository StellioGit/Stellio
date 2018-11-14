package com.example.anchit.stellio.Fragments;

import android.content.Context;
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

import com.example.anchit.stellio.R;
import com.example.anchit.stellio.SongsList;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.TreeMap;


/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link TestFragment.OnFragmentInteractionListener} interface
 * to handle interaction events.
 * Use the {@link TestFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class TestFragment extends Fragment {

    private ListView fragmentList;
    private ArrayAdapter arrayAdapter;
    private SongsList list = new SongsList();
    private HashMap<String,ArrayList<String>> finalAlbum;
    ArrayList<String> songs = new ArrayList<>();

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    private OnFragmentInteractionListener mListener;

    public TestFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment TestFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static TestFragment newInstance(String param1, String param2) {
        TestFragment fragment = new TestFragment();
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

        View view = inflater.inflate(R.layout.fragment_test, container, false);

        fragmentList = view.findViewById(R.id.fragmentList);

        Toast.makeText(getContext(), "Size: " + list.getSortedAlbumTitleTreeMap(getContext()).size(), Toast.LENGTH_SHORT).show();

        ArrayList<String> test = new ArrayList<>(list.getSortedAlbumTitleTreeMap(getContext()).keySet());

        arrayAdapter = new ArrayAdapter<>(getContext(),android.R.layout.simple_list_item_1,test);
        fragmentList.setAdapter(arrayAdapter);

        Toast.makeText(getContext(), "Test List Size:\n" + test.size(), Toast.LENGTH_LONG).show();

        Toast.makeText(getContext(), "Album-ID List Size:\n" + list.getAlbumID(getContext()).size(), Toast.LENGTH_LONG).show();


        fragmentList.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

                Toast.makeText(getContext(), "Entered in Event", Toast.LENGTH_LONG).show();

                Toast.makeText(getContext(), "Selected Album:\n" + fragmentList.getItemAtPosition(position), Toast.LENGTH_SHORT).show();

                getListAgain(fragmentList.getItemAtPosition(position).toString());

                Toast.makeText(getContext(), "Songs List Size:\n" + songs.size(), Toast.LENGTH_SHORT).show();

                /*getHashMap();

                Toast.makeText(getContext(), "Total Final Albums:\n" + finalAlbum.keySet().size(), Toast.LENGTH_LONG).show();

                String keyName = finalAlbum.entrySet().toArray()[position].toString();

                Toast.makeText(getContext(), "Received Album:\n" + keyName, Toast.LENGTH_LONG).show();

                ArrayList<String> songsFromAlbum = finalAlbum.get(keyName);

                Toast.makeText(getContext(), "Received Song Array Size:\n" + songsFromAlbum.size(), Toast.LENGTH_LONG).show();*/

                /*Intent it = new Intent(getContext(), AlbumSelectedSongs.class);
                Bundle bundle = new Bundle();
                bundle.putSerializable("ALBUM_CONTENT",songsFromAlbum);
                it.putExtra("ALBUM_LIST",bundle);
                it.putStringArrayListExtra("ALBUM_LIST",songsFromAlbum);
                startActivity(it);*/

            }
        });

        return view;
    }

    /*Testing Logic BEGINS
     *
     *
     */

    public void getListAgain(String album)  {
        Map<String,String> titleAlbum = new TreeMap<>(list.getSortedTitleAlbumTreeMap(getContext()));

        for (int i=0;i<titleAlbum.size();i++)   {
            if (titleAlbum.containsValue(album)) {
                for(Object o:titleAlbum.keySet()){
                    if(titleAlbum.get(o).equals(album)) {
                        songs.add((String) o);
                    }
                }
            }
        }
    }

    /*Testing Logic ENDS
     *
     *
     */

    public void getHashMap()   {
        Map<String,String> albumTitle = new TreeMap<>(list.getSortedAlbumTitleTreeMap(getContext()));
        Map<String,String> titleAlbum = new TreeMap<>(list.getSortedTitleAlbumTreeMap(getContext()));

        ArrayList<String> albumList = new ArrayList<>(titleAlbum.keySet());
        /*for(int i = 0;i<albumTitle.size();i++) {
            albumList.add(titleAlbum.keySet().toArray()[i].toString());
        }*/



        ArrayList<String> newAlbum = new ArrayList<>();
        ArrayList<String> newSongs = new ArrayList<>(list.getSongs(getContext()));
        finalAlbum = new HashMap<>();

        Toast.makeText(getContext(), "albumList size: " + albumList.size(), Toast.LENGTH_LONG).show();

        for (int i = 0;i<albumList.size();i++)  {
            String albumName = albumList.get(i);
            for (int j = 0;j<titleAlbum.size();j++)   {
                String key = titleAlbum.keySet().toArray()[j].toString();
                String value = titleAlbum.get(key);
                /*Toast.makeText(getContext(), "Entering IF....", Toast.LENGTH_SHORT).show();*/
                if (albumName.equals(value))  {
                    /*Toast.makeText(getContext(), "Entered IF", Toast.LENGTH_SHORT).show();*/
                    newAlbum.add(key);
                }
            }
            finalAlbum.put(albumName,newAlbum);
        }
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
