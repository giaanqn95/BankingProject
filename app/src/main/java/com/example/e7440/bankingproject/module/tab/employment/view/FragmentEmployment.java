package com.example.e7440.bankingproject.module.tab.employment.view;

import android.app.DatePickerDialog;
import android.content.Intent;
import android.text.InputType;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.EditorInfo;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.example.e7440.bankingproject.R;
import com.example.e7440.bankingproject.components.TimeHelper;
import com.example.e7440.bankingproject.components.view.MyCheckBox;
import com.example.e7440.bankingproject.components.view.MyEditText;
import com.example.e7440.bankingproject.components.view.MySpinner;
import com.example.e7440.bankingproject.components.view.MyTextView;
import com.example.e7440.bankingproject.components.view.MyTextViewDate;
import com.example.e7440.bankingproject.module.base.BaseFragment;
import com.example.e7440.bankingproject.module.config.Config;
import com.example.e7440.bankingproject.module.main.MainActivity;
import com.example.e7440.bankingproject.module.model.DetailTab;
import com.example.e7440.bankingproject.module.tab.employment.EmploymentGeneral;
import com.example.e7440.bankingproject.module.tab.employment.presenter.EmploymentPresenterImpl;
import com.example.e7440.bankingproject.module.upload.UploadActivity;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

import butterknife.BindView;

/**
 * Created by E7440 on 6/14/2018.
 */

public class FragmentEmployment extends BaseFragment implements EmploymentGeneral.EmploymentView {

    @BindView(R.id.btn_next_loan)
    Button mButtonNext;
    @BindView(R.id.btn_back_loan)
    Button mButtonBack;
    @BindView(R.id.ll_main)
    LinearLayout mLinearLayout;

    private List<DetailTab> mDetailTabs;

    private String joined;
    private String url;
    private EmploymentPresenterImpl mEmploymentPresenter;
    View rootView;
    private List<MyEditText> myEditTexts = new ArrayList<MyEditText>();
    private List<MyTextViewDate> myTextViewDates = new ArrayList<MyTextViewDate>();
    private List<MyCheckBox> myCheckBoxes = new ArrayList<MyCheckBox>();

    public FragmentEmployment() {
    }

    @Override
    protected View onCreateView(LayoutInflater inflater, ViewGroup container) {
        if (rootView == null) {

            rootView = inflater.inflate(R.layout.fragment_employment, container, false);
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
        mButtonNext.setOnClickListener(this);
        mButtonBack.setOnClickListener(this);
    }

    @Override
    protected void initPresenter() {
        url = Config.getInstance().getmUrlList().get(3).getLink();
        mEmploymentPresenter = new EmploymentPresenterImpl(this);
        mEmploymentPresenter.getTab(url);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.btn_back_loan: {
                ((MainActivity) getActivity()).setCurrentItem(2, true);
                break;
            }
            case R.id.btn_next_loan: {
                if (!checkEmpty()) {
                    showDialogError(R.string.error_empty);
                    return;
                }
                if (!checkEmptyTextViewDate()){
                    showDialogError(R.string.error_empty);
                    return;
                }
                if (!checkBox()){
                    showDialogError(R.string.error_empty);
                    return;
                }
                Intent intent = new Intent(getActivity(), UploadActivity.class);
                getActivity().startActivity(intent);
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
        LinearLayout.LayoutParams layoutParamsEmployment = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT,
                LinearLayout.LayoutParams.WRAP_CONTENT);
        layoutParamsEmployment.setMargins(10, 10, 10, 10);
        LinearLayout.LayoutParams layoutParamsIncom = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT,
                LinearLayout.LayoutParams.WRAP_CONTENT, (float) 0.5);
        layoutParamsIncom.setMargins(10, 10, 10, 10);

        LinearLayout mLayoutEmployment, mLayoutIncom;

        mLayoutEmployment = new LinearLayout(getActivity());
        mLayoutEmployment.setOrientation(LinearLayout.VERTICAL);
        mLayoutEmployment.setLayoutParams(layoutParamsIncom);

        mLayoutIncom = new LinearLayout(getActivity());
        mLayoutIncom.setLayoutParams(layoutParamsIncom);
        mLayoutIncom.setOrientation(LinearLayout.VERTICAL);

        for (int i = 0; i < mDetailTabs.size(); i++) {
            DetailTab mTab = mDetailTabs.get(i);
            if (mTab.getType().equals("textviewColumn")) {
                if (mTab.getColumn().equals("1")) {
                    MyTextView mTextViewTitles = new MyTextView(getActivity(), mTab.getLabel(), true);
                    mLayoutEmployment.addView(mTextViewTitles, layoutParamsEmployment);
                } else {
                    MyTextView mTextViewTitles = new MyTextView(getActivity(), mTab.getLabel(), true);
                    mLayoutIncom.addView(mTextViewTitles);
                }
            }
            if (mTab.getType().equals("spinner")) {
                if (mTab.getColumn().equals("1")) {
                    MySpinner mySpinner = new MySpinner(getActivity(), mTab.getLabel(), mTab.getValue());
                    mLayoutEmployment.addView(mySpinner, layoutParamsEmployment);
                } else {
                    MySpinner mySpinner = new MySpinner(getActivity(), mTab.getLabel(), mTab.getValue());
                    mLayoutIncom.addView(mySpinner, layoutParamsEmployment);
                }
            }
            if (mTab.getType().equals("edittext")) {
                if (mTab.getColumn().equals("1")) {
                    MyEditText myEditText = new MyEditText(getActivity(), mTab.getLabel(), EditorInfo.TYPE_CLASS_TEXT);
                    mLayoutEmployment.addView(myEditText, layoutParamsEmployment);
                    myEditTexts.add((myEditText));
                } else {
                    MyEditText myEditText = new MyEditText(getActivity(), mTab.getLabel(), EditorInfo.TYPE_CLASS_TEXT);
                    mLayoutIncom.addView(myEditText, layoutParamsEmployment);
                    myEditTexts.add((myEditText));
                }
            }
            if (mTab.getType().equals("edittextnumber")) {
                if (mTab.getColumn().equals("1")) {
                    MyEditText myEditText = new MyEditText(getActivity(), mTab.getLabel(), InputType.TYPE_CLASS_NUMBER, true);
                    mLayoutEmployment.addView(myEditText, layoutParamsEmployment);
                    myEditTexts.add((myEditText));

                } else if (mTab.getColumn().equals("2")) {
                    MyEditText myEditText = new MyEditText(getActivity(), mTab.getLabel(), InputType.TYPE_CLASS_NUMBER, true);
                    mLayoutIncom.addView(myEditText, layoutParamsEmployment);
                    myEditTexts.add((myEditText));
                }
            }
            if (mTab.getType().equals("edittextemail")) {
                if (mTab.getColumn().equals("1")) {
                    MyEditText myEditText = new MyEditText(getActivity(), mTab.getLabel(), InputType.TYPE_TEXT_VARIATION_EMAIL_ADDRESS);
                    mLayoutEmployment.addView(myEditText, layoutParamsEmployment);
                    myEditTexts.add((myEditText));
                } else if (mTab.getType().equals("2")) {
                    MyEditText myEditText = new MyEditText(getActivity(), mTab.getLabel(), InputType.TYPE_TEXT_VARIATION_EMAIL_ADDRESS);
                    mLayoutIncom.addView(myEditText, layoutParamsEmployment);
                    myEditTexts.add((myEditText));
                }
            }
            if (mTab.getType().equals("textviewDate")) {
                if (mTab.getColumn().equals("1")) {
                    MyTextViewDate myTextViewDate = new MyTextViewDate(getActivity(), mTab.getLabel(), getResources().getString(R.string.personal_date));
                    mLayoutEmployment.addView(myTextViewDate, layoutParamsEmployment);
                    myTextViewDates.add(myTextViewDate);
                } else if (mTab.getColumn().equals("2")) {
                    MyTextViewDate myTextViewDate = new MyTextViewDate(getActivity(), mTab.getLabel(), getResources().getString(R.string.personal_date));
                    mLayoutIncom.addView(myTextViewDate, layoutParamsEmployment);
                    myTextViewDates.add(myTextViewDate);
                }
            }
            if (mTab.getType().equals("checkbox")) {
                if (mTab.getColumn().equals("1")) {
                    MyCheckBox myCheckBox = new MyCheckBox(getActivity(), mTab.getLabel());
                    mLayoutEmployment.addView(myCheckBox, layoutParamsEmployment);
                    myCheckBoxes.add(myCheckBox);
                } else if (mTab.getColumn().equals("2")) {
                    MyCheckBox myCheckBox = new MyCheckBox(getActivity(), mTab.getLabel());
                    mLayoutIncom.addView(myCheckBox, layoutParamsEmployment);
                    myCheckBoxes.add(myCheckBox);
                }
            }
        }
        mLinearLayout.addView(mLayoutEmployment);
        mLinearLayout.addView(mLayoutIncom);
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
        for (int i = 0; i < myCheckBoxes.size(); i++){
            stringsCheckBox[i] = myCheckBoxes.get(i).isChecked().toString();
            if (stringsCheckBox[i].equals("false")){
                check = false;
            }
        }
        return check;
    }
}
