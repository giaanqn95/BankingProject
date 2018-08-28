package com.example.e7440.bankingproject.module.tab.personal.view;

import android.text.InputType;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.EditorInfo;
import android.widget.Button;
import android.widget.LinearLayout;

import com.example.e7440.bankingproject.R;
import com.example.e7440.bankingproject.components.message_dialog.DialogResultItem;
import com.example.e7440.bankingproject.components.view.MyCheckBox;
import com.example.e7440.bankingproject.components.view.MyEditText;
import com.example.e7440.bankingproject.components.view.MySpinner;
import com.example.e7440.bankingproject.components.view.MyTextView;
import com.example.e7440.bankingproject.components.view.MyTextViewDate;
import com.example.e7440.bankingproject.module.base.BaseFragment;
import com.example.e7440.bankingproject.module.config.Config;
import com.example.e7440.bankingproject.module.main.MainActivity;
import com.example.e7440.bankingproject.module.model.DetailTab;
import com.example.e7440.bankingproject.module.tab.personal.PersonalGeneral;
import com.example.e7440.bankingproject.module.tab.personal.presenter.PersonalPresenterImpl;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;

import static com.example.e7440.bankingproject.module.main.MainActivity.dataJSON;
import static com.example.e7440.bankingproject.module.tab.loan.view.FragmentLoan.jsonObjectToTal;

/**
 * Created by E7440 on 6/12/2018.
 */

public class FragmentPersonal extends BaseFragment implements PersonalGeneral.PersonalView {

    @BindView(R.id.btn_next_loan)
    Button mButtonNext;
    @BindView(R.id.btn_back_loan)
    Button mButtonBack;
    @BindView(R.id.ll_main)
    LinearLayout mLinearLayout;

    private List<DetailTab> mDetailTabs;
    View rootView;
    String url;
    private PersonalPresenterImpl mPersonalPresenter;
    public static List<MyEditText> myEditTexts = new ArrayList<MyEditText>();
    public static List<MySpinner> mySpinners = new ArrayList<MySpinner>();
    public static List<MyTextViewDate> myTextViewDates = new ArrayList<MyTextViewDate>();
    public static List<MyCheckBox> myCheckBoxes = new ArrayList<MyCheckBox>();
    static String data;
    static String nameTab;

    public FragmentPersonal() {
    }

    @Override
    protected View onCreateView(LayoutInflater inflater, ViewGroup container) {
        if (rootView == null) {

            rootView = inflater.inflate(R.layout.fragment_personal, container, false);
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
        url = Config.getInstance().getmUrlList().get(1).getLink();
        nameTab = Config.getInstance().getmToolbarsList().get(1).getName();
        mPersonalPresenter = new PersonalPresenterImpl(this);
        mPersonalPresenter.getTab(url);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.btn_back_loan: {
                ((MainActivity) getActivity()).setCurrentItem(0, true);
                break;
            }
            case R.id.btn_next_loan: {
                if (!checkEmptyEditText()) {
                    showDialogError(R.string.error_empty_editext);
                    return;
                }
                if (!checkEmptyTextViewDate()) {
                    showDialogError(R.string.error_empty_date);
                    return;
                }
                if (!checkBox()) {
                    showDialogError(R.string.error_checkbox);
                    return;
                }
                addData();
                ((MainActivity) getActivity()).setCurrentItem(2, true);
                break;
            }
        }
    }

    @Override
    public void onClickDialog(DialogResultItem dialogResulltItem) {
        super.onClickDialog(dialogResulltItem);
        switch (dialogResulltItem.getDialogId()) {

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
        LinearLayout.LayoutParams layoutParamsImformation = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT,
                LinearLayout.LayoutParams.WRAP_CONTENT);
        layoutParamsImformation.setMargins(10, 0, 10, 10);

        LinearLayout.LayoutParams layoutParamsPayments = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT,
                LinearLayout.LayoutParams.WRAP_CONTENT, (float) 0.5);
        layoutParamsPayments.setMargins(10, 10, 10, 10);

        LinearLayout mLayoutImformation, mLayoutPayments;

        mLayoutImformation = new LinearLayout(getActivity());
        mLayoutImformation.setOrientation(LinearLayout.VERTICAL);
        mLayoutImformation.setLayoutParams(layoutParamsPayments);

        mLayoutPayments = new LinearLayout(getActivity());
        mLayoutPayments.setLayoutParams(layoutParamsPayments);
        mLayoutPayments.setOrientation(LinearLayout.VERTICAL);

        for (int i = 0; i < mDetailTabs.size(); i++) {
            DetailTab mTab = mDetailTabs.get(i);
            if (mTab.getType().equals("textviewColumn")) {
                if (mTab.getColumn().equals("1")) {
                    MyTextView mTextViewTitles = new MyTextView(getActivity(), mTab.getLabel(), true);
                    mLayoutImformation.addView(mTextViewTitles, layoutParamsImformation);
                } else {
                    MyTextView mTextViewTitles = new MyTextView(getActivity(), mTab.getLabel(), true);
                    mLayoutPayments.addView(mTextViewTitles);
                }
            }
            if (mTab.getType().equals("spinner")) {
                if (mTab.getColumn().equals("1")) {
                    MySpinner mySpinner = new MySpinner(getActivity(), mTab.getLabel(), mTab.getValue());
                    mLayoutImformation.addView(mySpinner, layoutParamsImformation);
                    mySpinners.add(mySpinner);
                } else {
                    MySpinner mySpinner = new MySpinner(getActivity(), mTab.getLabel(), mTab.getValue());
                    mLayoutPayments.addView(mySpinner, layoutParamsImformation);
                    mySpinners.add(mySpinner);
                }
            }
            if (mTab.getType().equals("edittext")) {
                if (mTab.getColumn().equals("1")) {
                    MyEditText myEditText = new MyEditText(getActivity(), mTab.getLabel(), EditorInfo.TYPE_CLASS_TEXT);
                    mLayoutImformation.addView(myEditText, layoutParamsImformation);
                    myEditTexts.add((myEditText));

                } else {
                    MyEditText myEditText = new MyEditText(getActivity(), mTab.getLabel(), EditorInfo.TYPE_CLASS_TEXT);
                    mLayoutPayments.addView(myEditText, layoutParamsImformation);
                    myEditTexts.add((myEditText));
                }
            }
            if (mTab.getType().equals("edittextnumber")) {
                if (mTab.getColumn().equals("1")) {
                    MyEditText myEditText = new MyEditText(getActivity(), mTab.getLabel(), InputType.TYPE_CLASS_NUMBER, true);
                    mLayoutImformation.addView(myEditText, layoutParamsImformation);
                    myEditTexts.add((myEditText));

                } else if (mTab.getColumn().equals("2")) {
                    MyEditText myEditText = new MyEditText(getActivity(), mTab.getLabel(), InputType.TYPE_CLASS_NUMBER, true);
                    mLayoutPayments.addView(myEditText, layoutParamsImformation);
                    myEditTexts.add((myEditText));
                }
            }
            if (mTab.getType().equals("edittextemail")) {
                if (mTab.getColumn().equals("1")) {
                    MyEditText myEditText = new MyEditText(getActivity(), mTab.getLabel(), InputType.TYPE_TEXT_VARIATION_EMAIL_ADDRESS);
                    mLayoutImformation.addView(myEditText, layoutParamsImformation);
                    myEditTexts.add((myEditText));

                } else if (mTab.getType().equals("2")) {
                    MyEditText myEditText = new MyEditText(getActivity(), mTab.getLabel(), InputType.TYPE_TEXT_VARIATION_EMAIL_ADDRESS);
                    mLayoutPayments.addView(myEditText, layoutParamsImformation);
                    myEditTexts.add((myEditText));
                }
            }
            if (mTab.getType().equals("textviewDate")) {
                if (mTab.getColumn().equals("1")) {
                    MyTextViewDate myTextViewDate = new MyTextViewDate(getActivity(), mTab.getLabel(), getResources().getString(R.string.personal_date));
                    mLayoutImformation.addView(myTextViewDate, layoutParamsImformation);
                    myTextViewDates.add(myTextViewDate);
                } else if (mTab.getColumn().equals("2")) {
                    MyTextViewDate myTextViewDate = new MyTextViewDate(getActivity(), mTab.getLabel(), getResources().getString(R.string.personal_date));
                    mLayoutPayments.addView(myTextViewDate, layoutParamsImformation);
                    myTextViewDates.add(myTextViewDate);
                }
            }
            if (mTab.getType().equals("checkbox")) {
                if (mTab.getColumn().equals("1")) {
                    MyCheckBox myCheckBox = new MyCheckBox(getActivity(), mTab.getLabel());
                    mLayoutImformation.addView(myCheckBox, layoutParamsImformation);
                    myCheckBoxes.add(myCheckBox);
                } else if (mTab.getColumn().equals("2")) {
                    MyCheckBox myCheckBox = new MyCheckBox(getActivity(), mTab.getLabel());
                    mLayoutPayments.addView(myCheckBox, layoutParamsImformation);
                    myCheckBoxes.add(myCheckBox);
                }
            }
        }
        mLinearLayout.addView(mLayoutImformation);
        mLinearLayout.addView(mLayoutPayments);
    }

    private boolean checkEmptyEditText() {
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

    public static void addData() {

        JSONObject jsonObject = new JSONObject();
        String[] stringsEditText = new String[myEditTexts.size()];
        String[] stringsSpinner = new String[mySpinners.size()];
        String[] stringTextView = new String[myTextViewDates.size()];
        String[] stringsCheckbox = new String[myCheckBoxes.size()];
        for (int i = 0; i < myEditTexts.size(); i++) {
            try {
                if (!myEditTexts.get(i).getValue().toString().equals("")) {
                    String value = stringsEditText[i] = myEditTexts.get(i).getValue().toString();
                    String name = stringsEditText[i] = myEditTexts.get(i).getLabel().toString();
                    jsonObject.put(name, value);
                    jsonObjectToTal.put(nameTab, jsonObject);
                }
            } catch (Exception e) {
            }
        }
        for (int i = 0; i < mySpinners.size(); i++) {
            try {
                String value = stringsSpinner[i] = mySpinners.get(i).getValue().toString();
                String name = stringsSpinner[i] = mySpinners.get(i).getLabel().toString();
                jsonObject.put(name, value);
                jsonObjectToTal.put(nameTab, jsonObject);
            } catch (Exception e) {
            }
        }
        for (int i = 0; i < myCheckBoxes.size(); i++) {
            try {
                String value = stringsCheckbox[i] = myCheckBoxes.get(i).isChecked().toString();
                String name = stringsCheckbox[i] = myCheckBoxes.get(i).getLabel().toString();
                jsonObject.put(name, value);
                jsonObjectToTal.put(nameTab, jsonObject);
            } catch (Exception e) {
            }
        }
        for (int i = 0; i < myTextViewDates.size(); i++) {
            try {
                String value = stringTextView[i] = myTextViewDates.get(i).getValue().toString();
                String name = stringTextView[i] = myTextViewDates.get(i).getLabel().toString();
                jsonObject.put(name, value);
                jsonObjectToTal.put(nameTab, jsonObject);
            } catch (Exception e) {
            }
        }
        data = String.valueOf(jsonObjectToTal);
    }
}
