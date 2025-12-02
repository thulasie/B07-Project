package com.example.smartAir.ui.technique;

import android.net.Uri;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.MediaController;
import android.widget.VideoView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.smartAir.R;

import java.util.Arrays;
import java.util.List;

public class TechniqueHelperFragment extends Fragment {

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_technique_helper, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        RecyclerView rv = view.findViewById(R.id.rv_steps);
        rv.setLayoutManager(new LinearLayoutManager(getContext(), LinearLayoutManager.HORIZONTAL, false));
        List<String> steps = Arrays.asList(
                "Wrap lips around inhaler",
                "Take a slow and deep breath",
                "Hold your breath for approx. 10 seconds",
                "Wait 30 to 60 seconds between puffs",
                "Spacer/mask tips"
        );
        TechniqueStepsAdapter adapter = new TechniqueStepsAdapter(steps);
        rv.setAdapter(adapter);

        // add video??????

        VideoView videoView = view.findViewById(R.id.videoView);

        String videoPath = "android.resource://" + getActivity().getPackageName() + "/" + R.raw.tutorial;
        Uri uri = Uri.parse(videoPath);

        videoView.setVideoURI(uri);

        MediaController mediaController = new MediaController(getActivity());
        videoView.setMediaController(mediaController);
        mediaController.setAnchorView(videoView);

        videoView.start();

    }
}

