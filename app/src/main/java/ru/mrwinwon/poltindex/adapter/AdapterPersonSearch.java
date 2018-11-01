package ru.mrwinwon.poltindex.adapter;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Filter;
import android.widget.Filterable;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.List;

import ru.mrwinwon.poltindex.R;
import ru.mrwinwon.poltindex.adapter.util.CircleTransform;
import ru.mrwinwon.poltindex.model.Person;

public class AdapterPersonSearch extends ArrayAdapter<Person> implements Filterable {
    private Context context;
    private List<Person>originalData = null;
    private List<Person>filteredData = null;
    private LayoutInflater mInflater;
    private ItemFilter mFilter = new ItemFilter();

    public AdapterPersonSearch(@NonNull Context context, int resource, @NonNull List<Person> people) {
        super(context, resource, people);
        this.context = context;
        this.originalData = people;
        this.filteredData = people;

    }

    public int getCount() {
        return filteredData.size();
    }

    public Person getItem(int position) {
        return filteredData.get(position);
    }

    public long getItemId(int position) {
        return position;
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        // A ViewHolder keeps references to children views to avoid unnecessary calls
        // to findViewById() on each row.
        ViewHolder holder;

        // When convertView is not null, we can reuse it directly, there is no need
        // to reinflate it. We only inflate a new View when the convertView supplied
        // by ListView is null.
        Person person = getItem(position);
        if (convertView == null) {
            convertView = LayoutInflater.from(context).inflate(R.layout.person_list_item, parent, false);

            // Creates a ViewHolder and store references to the two children views
            // we want to bind data to.
            holder = new ViewHolder();
            holder.firstName = convertView.findViewById(R.id.tv_person_firstname_list);
            holder.secondName = convertView.findViewById(R.id.tv_person_secondname_list);
            holder.rating = convertView.findViewById(R.id.tv_person_rating_list);
            holder.personAvatar = convertView.findViewById(R.id.ib_round_person_list);

            // Bind the data efficiently with the holder.

            convertView.setTag(holder);
        } else {
            // Get the ViewHolder back to get fast access to the TextView
            // and the ImageView.
            holder = (ViewHolder) convertView.getTag();
        }

        // If weren't re-ordering this you could rely on what you set last time
        holder.firstName.setText(filteredData.get(position).getFirstName());
        holder.secondName.setText(filteredData.get(position).getSecondName());
        holder.rating.setText(filteredData.get(position).getRating() + "");
        holder.firstName.setText(filteredData.get(position).getFirstName());
        Picasso.with(context).load(person.getAvatarSmall()).transform(new CircleTransform()).into(holder.personAvatar);

        return convertView;
    }

    static class ViewHolder {
        TextView firstName;
        TextView secondName;
        TextView rating;
        ImageView personAvatar;
    }

    @NonNull
    public Filter getFilter() {
        return mFilter;
    }

    private class ItemFilter extends Filter {
        @Override
        protected FilterResults performFiltering(CharSequence constraint) {

            String filterString = constraint.toString().toLowerCase();

            FilterResults results = new FilterResults();

            final List<Person> list = originalData;

            int count = list.size();
            final ArrayList<Person> nlist = new ArrayList<>(count);

            String filterableString ;

            for (int i = 0; i < count; i++) {
                filterableString = list.get(i).getFirstName() + list.get(i).getSecondName();
                if (filterableString.toLowerCase().contains(filterString)) {
                    nlist.add(list.get(i));
                }
            }

            results.values = nlist;
            results.count = nlist.size();

            return results;
        }

        @SuppressWarnings("unchecked")
        @Override
        protected void publishResults(CharSequence constraint, FilterResults results) {
            filteredData = (ArrayList<Person>) results.values;
            notifyDataSetChanged();
        }

    }
}
