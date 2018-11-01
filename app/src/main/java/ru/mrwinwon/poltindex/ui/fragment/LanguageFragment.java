package ru.mrwinwon.poltindex.ui.fragment;

import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.ArrayList;

import ru.mrwinwon.poltindex.R;
import ru.mrwinwon.poltindex.adapter.MyLanguageRecyclerViewAdapter;
import ru.mrwinwon.poltindex.database.SessionManager;
import ru.mrwinwon.poltindex.model.Language;
import ru.mrwinwon.poltindex.util.Const;

public class LanguageFragment extends Fragment implements MyLanguageRecyclerViewAdapter.ClickListener{

    private ArrayList<Language> languages;
    private OnListFragmentInteractionListener mListener;
    private Context context;
    private SessionManager sessionManager;

    public LanguageFragment() {
    }

    @SuppressWarnings("unused")
    public static LanguageFragment newInstance(ArrayList<Language> languages) {
        LanguageFragment fragment = new LanguageFragment();
        Bundle args = new Bundle();
        args.putSerializable(Const.ARG_LANGUAGES, languages);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        if (getArguments() != null) {
            languages = (ArrayList<Language>) getArguments().getSerializable(Const.ARG_LANGUAGES);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_language_list, container, false);

        // Set the adapter
        if (view instanceof RecyclerView) {
            context = view.getContext();
            RecyclerView recyclerView = (RecyclerView) view;
            recyclerView.setLayoutManager(new LinearLayoutManager(context));
            MyLanguageRecyclerViewAdapter adapter = new MyLanguageRecyclerViewAdapter(languages, mListener);
            adapter.setOnItemClickListener(this);
            recyclerView.setAdapter(adapter);
            sessionManager = new SessionManager(context);
        }
        return view;
    }


    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        if (context instanceof OnListFragmentInteractionListener) {
            mListener = (OnListFragmentInteractionListener) context;
        } else {

        }
    }

    @Override
    public void onDetach() {
        super.onDetach();
        mListener = null;
    }

    @Override
    public void onLanguageClick(String language) {
        sessionManager.setKeyCurrentLanguage(language.substring(0,2).toUpperCase());
        sessionManager.setKeyLocaleChanged(true);
        getActivity().finish();
    }

    public interface OnListFragmentInteractionListener {
        void onListFragmentInteraction(Language item);
    }
}
