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
import ru.mrwinwon.poltindex.adapter.MyCountryRecyclerViewAdapter;
import ru.mrwinwon.poltindex.database.SessionManager;
import ru.mrwinwon.poltindex.model.Country;
import ru.mrwinwon.poltindex.util.Const;

public class CountryFragment extends Fragment implements MyCountryRecyclerViewAdapter.ClickListener {

    private ArrayList<Country> countries;
    private OnListFragmentInteractionListener mListener;
    private Context context;
    private SessionManager sessionManager;

    public CountryFragment() {
    }

    @SuppressWarnings("unused")
    public static CountryFragment newInstance(ArrayList<Country> countries) {
        CountryFragment fragment = new CountryFragment();
        Bundle args = new Bundle();
        args.putSerializable(Const.ARG_COUNTRIES, countries);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        if (getArguments() != null) {
            countries = (ArrayList<Country>) getArguments().getSerializable(Const.ARG_COUNTRIES);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_country_list, container, false);

        // Set the adapter
        if (view instanceof RecyclerView) {
            context = view.getContext();
            RecyclerView recyclerView = (RecyclerView) view;
            recyclerView.setLayoutManager(new LinearLayoutManager(context));
            MyCountryRecyclerViewAdapter adapter = new MyCountryRecyclerViewAdapter(countries, mListener);
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
    public void onCountryClick(String country) {
        sessionManager.setKeyCurrentCountry(country.substring(0,2).toUpperCase());
        getActivity().finish();
    }

    public interface OnListFragmentInteractionListener {
        void onListFragmentInteraction(Country item);
    }
}
