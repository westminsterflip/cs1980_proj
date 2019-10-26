package com.example.medicationadherence.ui.medications.wizard;


import android.os.AsyncTask;
import android.os.Bundle;
import android.util.JsonReader;
import android.util.JsonToken;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.medicationadherence.R;
import com.example.medicationadherence.adapter.ImageSelectorAdapter;

import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.URL;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.concurrent.ExecutionException;

import javax.net.ssl.HttpsURLConnection;

public class WizardImageSelector extends Fragment implements RootWizardFragment.ErrFragment {
    RootWizardViewModel model;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        model = new ViewModelProvider(getParentFragment().getParentFragment()).get(RootWizardViewModel.class);
        if (model.getThisList().size() == 1)
            model.getThisList().add(this);
        else if (model.getThisList().get(1) != this)
            model.getThisList().set(1, this);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View root = inflater.inflate(R.layout.fragment_wizard_image_selector, container, false);
        RecyclerView imageList = root.findViewById(R.id.imageRecyclerView);
        imageList.setLayoutManager(new GridLayoutManager(getContext(), 3));
        imageList.setHasFixedSize(true);
        ArrayList<String> images = null;
        try {
            images = new MedImageTask(model, 1).execute().get();
        } catch (ExecutionException | InterruptedException e) {
            e.printStackTrace();
        }
        if (images != null) {
            imageList.setAdapter(new ImageSelectorAdapter(images));
        }

        return root;
    }

    @Override
    public void showErrors() {

    }

    @Override
    public void pause() {

    }

    @Override
    public boolean isExitable() {
        return true;
    }
//TODO: cache images, select images, filter images. ppage up when bottom hit
    private static class MedImageTask extends AsyncTask<Void, Void, ArrayList<String>> {
        RootWizardViewModel model;
        int page;

        public MedImageTask(RootWizardViewModel model, int page) {
            this.model = model;
            this.page = page;
        }

        @Override
        protected ArrayList<String> doInBackground(Void... voids) {
            try {
                ArrayList<String> out = new ArrayList<>();
                URL medAPI = new URL("https://rximage.nlm.nih.gov/api/rximage/1/rxnav?name=" + model.getMedName() + "&rPage=" + page);
                HttpsURLConnection apiConn = (HttpsURLConnection) medAPI.openConnection();
                if (apiConn.getResponseCode() == 200) {
                    InputStream response = apiConn.getInputStream();
                    InputStreamReader responseReader = new InputStreamReader(response, StandardCharsets.UTF_8);
                    JsonReader jsonReader = new JsonReader(responseReader);
                    JsonToken token = jsonReader.peek();
                    while (token != JsonToken.END_DOCUMENT) {
                        switch (token) {
                            case BEGIN_OBJECT:
                                jsonReader.beginObject();
                                break;
                            case BEGIN_ARRAY:
                                jsonReader.beginArray();
                                break;
                            case BOOLEAN:
                                jsonReader.nextBoolean();
                                break;
                            case END_ARRAY:
                                jsonReader.endArray();
                                break;
                            case END_OBJECT:
                                jsonReader.endObject();
                                break;
                            case NAME:
                                String j = jsonReader.nextName();
                                if (j.equals("imageUrl")) {
                                    out.add(jsonReader.nextString());
                                    System.out.println(out.get(out.size() - 1));
                                } else {
                                    System.out.println("key: " + j);
                                }
                                break;
                            case NULL:
                                jsonReader.nextNull();
                                break;
                            case NUMBER:
                                System.out.println("num: " + jsonReader.nextDouble());
                                break;
                            case STRING:
                                System.out.println("string: " + jsonReader.nextString());
                                break;
                            default:
                                System.out.println("UNKNOWN");
                        }
                        token = jsonReader.peek();
                        System.out.println(token.name());
                    }
                    jsonReader.close();
                    apiConn.disconnect();
                    return out;
                } else {
                    try {
                        throw new Exception("API connection failed");
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
            return null;
        }
    }
}
