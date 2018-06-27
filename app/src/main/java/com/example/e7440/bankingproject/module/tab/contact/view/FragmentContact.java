package com.example.e7440.bankingproject.module.tab.contact.view;

import android.text.InputType;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.EditorInfo;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.example.e7440.bankingproject.R;
import com.example.e7440.bankingproject.components.view.MyCheckBox;
import com.example.e7440.bankingproject.components.view.MyEditText;
import com.example.e7440.bankingproject.components.view.MySpinner;
import com.example.e7440.bankingproject.components.view.MyTextView;
import com.example.e7440.bankingproject.components.view.MyTextViewDate;
import com.example.e7440.bankingproject.module.base.BaseFragment;
import com.example.e7440.bankingproject.module.config.Config;
import com.example.e7440.bankingproject.module.main.MainActivity;
import com.example.e7440.bankingproject.module.model.DetailTab;
import com.example.e7440.bankingproject.module.model.Item;
import com.example.e7440.bankingproject.module.tab.contact.ContactGeneral;
import com.example.e7440.bankingproject.module.tab.contact.presenter.ContactPresenterImpl;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;

import static com.example.e7440.bankingproject.module.main.MainActivity.dataJSON;

/**
 * Created by E7440 on 6/14/2018.
 */

public class FragmentContact extends BaseFragment implements ContactGeneral.ContactView {

    @BindView(R.id.btn_back_loan)
    Button mButtonBack;
    @BindView(R.id.btn_next_loan)
    Button mButtonNext;
    @BindView(R.id.ll_main)
    LinearLayout mLinearLayout;

    String url;
    private ContactPresenterImpl mContactPresenter;
    private List<DetailTab> mDetailTabs;
    View rootView;
    private List<MyEditText> myEditTexts = new ArrayList<MyEditText>();
    private List<MyTextViewDate> myTextViewDates = new ArrayList<MyTextViewDate>();
    private List<MyCheckBox> myCheckBoxes = new ArrayList<MyCheckBox>();
    private List<MySpinner> mySpinners = new ArrayList<MySpinner>();
    String data;

    public FragmentContact() {
    }

    @Override
    protected View onCreateView(LayoutInflater inflater, ViewGroup container) {
        if (rootView == null) {
            rootView = inflater.inflate(R.layout.fragment_contact, container, false);
        }

        return rootView;
    }

    @Override
    protected void setDataToUI() {
        mDetailTabs = new ArrayList<>();
        mLinearLayout.removeAllViews();
    }

    @Override
    protected void addActionClickListener() {
        mButtonBack.setOnClickListener(this);
        mButtonNext.setOnClickListener(this);
    }

    @Override
    protected void initPresenter() {
        url = Config.getInstance().getmUrlList().get(2).getLink();
        mContactPresenter = new ContactPresenterImpl(this);
        mContactPresenter.getTab(url);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.tv_relationship: {

                break;
            }
            case R.id.btn_back_loan: {
                ((MainActivity) getActivity()).setCurrentItem(1, true);
                break;
            }
            case R.id.btn_next_loan: {
                if (!checkEmpty()) {
                    showDialogError(R.string.error_empty);
                    return;
                }
                if (!checkEmptyTextViewDate()) {
                    showDialogError(R.string.error_empty);
                    return;
                }
                if (!checkBox()) {
                    showDialogError(R.string.error_empty);
                    return;
                }
                addData();
                ((MainActivity) getActivity()).setCurrentItem(3, true);
                break;
            }
        }
    }

    @Override
    public void fetchTab(List<DetailTab> mExampleList) {
        if (mExampleList == null) {

        } else {
            mDetailTabs.clear();
            mDetailTabs.addAll(mExampleList);
            addView();
        }
    }

    public void addView() {
        LinearLayout.LayoutParams layoutParamsContact = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT,
                LinearLayout.LayoutParams.WRAP_CONTENT);
        layoutParamsContact.setMargins(10, 10, 10, 10);
        LinearLayout.LayoutParams layoutParamsReference = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT,
                LinearLayout.LayoutParams.WRAP_CONTENT, (float) 0.5);
        layoutParamsReference.setMargins(10, 10, 10, 10);

        LinearLayout mLayoutContact, mLayoutReference;

        mLayoutContact = new LinearLayout(getActivity());
        mLayoutContact.setOrientation(LinearLayout.VERTICAL);
        mLayoutContact.setLayoutParams(layoutParamsReference);

        mLayoutReference = new LinearLayout(getActivity());
        mLayoutReference.setLayoutParams(layoutParamsReference);
        mLayoutReference.setOrientation(LinearLayout.VERTICAL);

        for (int i = 0; i < mDetailTabs.size(); i++) {
            DetailTab mTab = mDetailTabs.get(i);
            if (mTab.getType().equals("textviewColumn")) {
                if (mTab.getColumn().equals("1")) {
                    MyTextView mTextViewTitles = new MyTextView(getActivity(), mTab.getLabel(), true);
                    mLayoutContact.addView(mTextViewTitles, layoutParamsContact);
                } else {
                    MyTextView mTextViewTitles = new MyTextView(getActivity(), mTab.getLabel(), true);
                    mLayoutReference.addView(mTextViewTitles);
                }
            }
            if (mTab.getType().equals("spinner")) {
                if (mTab.getColumn().equals("1")) {
                    MySpinner mySpinner = new MySpinner(getActivity(), mTab.getLabel(), mTab.getValue());
                    mLayoutContact.addView(mySpinner, layoutParamsContact);
                    mySpinners.add(mySpinner);
                } else {
                    MySpinner mySpinner = new MySpinner(getActivity(), mTab.getLabel(), mTab.getValue());
                    mLayoutReference.addView(mySpinner, layoutParamsContact);
                    mySpinners.add(mySpinner);
                }
            }
            if (mTab.getType().equals("edittext")) {
                if (mTab.getColumn().equals("1")) {
                    MyEditText myEditText = new MyEditText(getActivity(), mTab.getLabel(), EditorInfo.TYPE_CLASS_TEXT);
                    mLayoutContact.addView(myEditText, layoutParamsContact);
                    myEditTexts.add((myEditText));
                } else {
                    MyEditText myEditText = new MyEditText(getActivity(), mTab.getLabel(), EditorInfo.TYPE_CLASS_TEXT);
                    mLayoutReference.addView(myEditText, layoutParamsContact);
                    myEditTexts.add((myEditText));
                }
            }
            if (mTab.getType().equals("edittextnumber")) {
                if (mTab.getColumn().equals("1")) {
                    MyEditText myEditText = new MyEditText(getActivity(), mTab.getLabel(), InputType.TYPE_CLASS_NUMBER, true);
                    mLayoutContact.addView(myEditText, layoutParamsContact);
                    myEditTexts.add((myEditText));

                } else if (mTab.getColumn().equals("2")) {
                    MyEditText myEditText = new MyEditText(getActivity(), mTab.getLabel(), InputType.TYPE_CLASS_NUMBER, true);
                    mLayoutReference.addView(myEditText, layoutParamsContact);
                    myEditTexts.add((myEditText));
                }
            }
            if (mTab.getType().equals("edittextemail")) {
                if (mTab.getColumn().equals("1")) {
                    MyEditText myEditText = new MyEditText(getActivity(), mTab.getLabel(), InputType.TYPE_TEXT_VARIATION_EMAIL_ADDRESS);
                    mLayoutContact.addView(myEditText, layoutParamsContact);
                    myEditTexts.add((myEditText));
                } else if (mTab.getType().equals("2")) {
                    MyEditText myEditText = new MyEditText(getActivity(), mTab.getLabel(), InputType.TYPE_TEXT_VARIATION_EMAIL_ADDRESS);
                    mLayoutReference.addView(myEditText, layoutParamsContact);
                    myEditTexts.add((myEditText));
                }
            }
            if (mTab.getType().equals("textviewDate")) {
                if (mTab.getColumn().equals("1")) {
                    MyTextViewDate myTextViewDate = new MyTextViewDate(getActivity(), mTab.getLabel(), getResources().getString(R.string.personal_date));
                    mLayoutContact.addView(myTextViewDate, layoutParamsContact);
                    myTextViewDates.add(myTextViewDate);
                } else if (mTab.getColumn().equals("2")) {
                    MyTextViewDate myTextViewDate = new MyTextViewDate(getActivity(), mTab.getLabel(), getResources().getString(R.string.personal_date));
                    mLayoutReference.addView(myTextViewDate, layoutParamsContact);
                    myTextViewDates.add(myTextViewDate);
                }
            }
            if (mTab.getType().equals("checkbox")) {
                if (mTab.getColumn().equals("1")) {
                    MyCheckBox myCheckBox = new MyCheckBox(getActivity(), mTab.getLabel());
                    mLayoutContact.addView(myCheckBox, layoutParamsContact);
                    myCheckBoxes.add(myCheckBox);
                } else if (mTab.getColumn().equals("2")) {
                    MyCheckBox myCheckBox = new MyCheckBox(getActivity(), mTab.getLabel());
                    mLayoutReference.addView(myCheckBox, layoutParamsContact);
                    myCheckBoxes.add(myCheckBox);
                }
            }
        }
        mLinearLayout.addView(mLayoutContact);
        mLinearLayout.addView(mLayoutReference);
    }

    private boolean checkEmpty() {
        String[] stringsEditText = new String[myEditTexts.size()];
        Boolean check = true;
        for (int i = 0; i < myEditTexts.size(); i++) {
            stringsEditText[i] = myEditTexts.get(i).getValue().toString();
            if (stringsEditText[i].equals("")) {
                check = false;
            }
        }
        return check;
    }

    private boolean checkEmptyTextViewDate() {
        String[] stringTextView = new String[myTextViewDates.size()];
        Boolean check = true;
        for (int i = 0; i < myTextViewDates.size(); i++) {
            stringTextView[i] = myTextViewDates.get(i).getValue().toString();
            if (stringTextView[i].equals("")) {
                check = false;
            }
        }
        return check;
    }

    private boolean checkBox() {
        String[] stringsCheckBox = new String[myCheckBoxes.size()];
        Boolean check = true;
        for (int i = 0; i < myCheckBoxes.size(); i++) {
            stringsCheckBox[i] = myCheckBoxes.get(i).isChecked().toString();
            if (stringsCheckBox[i].equals("false")) {
                check = false;
            }
        }
        return check;
    }

    private void addData() {
        JSONArray jsonArray = new JSONArray();
        JSONObject jsonObject = null;
        String[] stringsEditText = new String[myEditTexts.size()];
        String[] stringsSpinner = new String[mySpinners.size()];
        String[] stringTextView = new String[myTextViewDates.size()];
        String[] stringsCheckbox = new String[myCheckBoxes.size()];
        for (int i = 0; i < myEditTexts.size(); i++) {
            jsonObject = new JSONObject();
            try {
                if (!myEditTexts.get(i).getValue().toString().equals("")) {
                    String value = stringsEditText[i] = myEditTexts.get(i).getValue().toString();
                    String name = stringsEditText[i] = myEditTexts.get(i).getLabel().toString();
                    jsonObject.put(name, value);
                }
            } catch (Exception e) {
            }
            jsonArray.put(jsonObject);
        }
        for (int i = 0; i < mySpinners.size(); i++) {
            jsonObject = new JSONObject();
            try {
                String value = stringsSpinner[i] = mySpinners.get(i).getValue().toString();
                String name = stringsSpinner[i] = mySpinners.get(i).getLabel().toString();
                jsonObject.put(name, value);
            } catch (Exception e) {
            }
            jsonArray.put(jsonObject);
        }
        for (int i = 0; i < myCheckBoxes.size(); i++) {
            jsonObject = new JSONObject();
            try {
                String value = stringsCheckbox[i] = myCheckBoxes.get(i).isChecked().toString();
                String name = stringsCheckbox[i] = myCheckBoxes.get(i).getLabel().toString();
                jsonObject.put(name, value);
            } catch (Exception e) {

            }
            jsonArray.put(jsonObject);

        }
        for (int i = 0; i < myTextViewDates.size(); i++) {
            jsonObject = new JSONObject();
            try {
                String value = stringTextView[i] = myTextViewDates.get(i).getValue().toString();
                String name = stringTextView[i] = myTextViewDates.get(i).getLabel().toString();
                jsonObject.put(name, value);
            } catch (Exception e) {
            }
           jsonArray.put(jsonObject);
        }
        data = String.valueOf(jsonArray);
        dataJSON += data + "\n";
        Log.d("AAAAA", "" + data);
//        EventBus.getDefault().postSticky(data);
    }
}
