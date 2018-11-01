package ru.mrwinwon.poltindex.adapter.util;

import com.thoughtbot.expandablerecyclerview.models.ExpandableGroup;

import java.util.List;

import ru.mrwinwon.poltindex.model.Timeline;

public class TimelineItem extends ExpandableGroup<Timeline> {

    public TimelineItem (String title, List<Timeline> items) {
        super(title, items);
    }
}
