package com.bill24.bill24onlinepaymentsdk.adapter;

import android.annotation.SuppressLint;
import android.content.Context;
import android.graphics.Color;
import android.graphics.ColorFilter;
import android.graphics.Paint;
import android.graphics.PorterDuff;
import android.graphics.PorterDuffColorFilter;
import android.graphics.Typeface;
import android.graphics.drawable.ShapeDrawable;
import android.graphics.drawable.StateListDrawable;
import android.os.Build;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;

import androidx.annotation.NonNull;
import androidx.appcompat.widget.AppCompatImageView;
import androidx.appcompat.widget.AppCompatTextView;
import androidx.appcompat.widget.LinearLayoutCompat;
import androidx.coordinatorlayout.widget.CoordinatorLayout;
import androidx.core.content.res.ResourcesCompat;
import androidx.recyclerview.widget.RecyclerView;

import com.bill24.bill24onlinepaymentsdk.R;
import com.bill24.bill24onlinepaymentsdk.customShapeDrawable.CustomShape;
import com.bill24.bill24onlinepaymentsdk.customShapeDrawable.SelectedState;
import com.bill24.bill24onlinepaymentsdk.helper.ConvertColorHexa;
import com.bill24.bill24onlinepaymentsdk.helper.SetFont;
import com.bill24.bill24onlinepaymentsdk.helper.StickyHeaderItemDecoration;
import com.bill24.bill24onlinepaymentsdk.model.AddToFavoriteModel;
import com.bill24.bill24onlinepaymentsdk.model.BankPaymentMethodItemModel;
import com.bill24.bill24onlinepaymentsdk.model.BankPaymentMethodModel;
import com.bill24.bill24onlinepaymentsdk.model.CheckoutPageConfigModel;
import com.bill24.bill24onlinepaymentsdk.model.TransactionInfoModel;
import com.bill24.bill24onlinepaymentsdk.model.appearance.darkMode.DarkModeModel;
import com.bill24.bill24onlinepaymentsdk.model.appearance.lightMode.LightModeModel;
import com.bill24.bill24onlinepaymentsdk.model.baseResponseModel.BaseResponse;
import com.bill24.bill24onlinepaymentsdk.model.conts.Constant;
import com.bill24.bill24onlinepaymentsdk.model.conts.CurrencyCode;
import com.bill24.bill24onlinepaymentsdk.model.conts.LanguageCode;
import com.bill24.bill24onlinepaymentsdk.core.RetrofitClient;
import com.bill24.bill24onlinepaymentsdk.model.requestModel.AddToFavoriteRequestModel;
import com.squareup.picasso.Picasso;

import java.text.DecimalFormat;
import java.text.NumberFormat;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class PaymentMethodAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder>
        implements StickyHeaderItemDecoration.StickyHeaderInterface {

    private static final int VIEW_HEADER_TYPE=0;
    private static final int VIEW_ITEM_TYPE=1;
    private CheckoutPageConfigModel checkoutPageConfigModel;

    private TransactionInfoModel transactionInfoModel;
    private List<BankPaymentMethodModel> bankPaymentMethodModelList;
    private PaymentMethodClickListener listener;

    private LinearLayoutCompat sectionContainer;
    private AppCompatTextView textSectionHeader,
            textBankName;
//            textServiceTitle,
//            textServiceAmount,
//            textCurrency;
    private String tranasctionId, refererKey,language;
    private boolean isLightMode;

   public void setPaymentMethod(
           CheckoutPageConfigModel checkoutPageConfigModel,
           TransactionInfoModel transactionInfoModel,
           List<BankPaymentMethodModel> bankPaymentMethodModelList,
           String transactionId,
           String refererKey,
           boolean isLightMode,
           String language){
       this.checkoutPageConfigModel=checkoutPageConfigModel;
       this.transactionInfoModel=transactionInfoModel;
       this.bankPaymentMethodModelList=bankPaymentMethodModelList;
       this.tranasctionId=transactionId;
       this.refererKey=refererKey;
       this.isLightMode=isLightMode;
       this.language=language;

       notifyDataSetChanged();
   }
    public void setOnItemClickListener(PaymentMethodClickListener listener){
        this.listener=listener;
    }

    private void initItemView(View view){
        textBankName=view.findViewById(R.id.text_bank_name);

        //textServiceTitle=view.findViewById(R.id.text_payment_service_title);
        //textServiceAmount=view.findViewById(R.id.text_payment_service_amount);
        //textCurrency=view.findViewById(R.id.text_payment_service_amount);
    }

    private void updateHeaderFont(Context context,AppCompatTextView textSectionHeader){
        SetFont font=new SetFont();
        Typeface typeface=font.setFont(context, language);
        textSectionHeader.setTypeface(typeface);
        textSectionHeader.setTextSize(11);
        textSectionHeader.setPaintFlags(Paint.FAKE_BOLD_TEXT_FLAG);
        textSectionHeader.setTextColor(context.getResources().getColor(R.color.header_font_color));
    }

    private  void applyHeaderStyleLightMode(LinearLayoutCompat headerContainer){
        LightModeModel lightModeModel=checkoutPageConfigModel.getAppearance().getLightMode();

        String bgHeaderColor=lightModeModel.getLabelBackgroundColor();
        String bgHeaderColorHexa=ConvertColorHexa.convertHex(bgHeaderColor);
        headerContainer.setBackgroundColor(Color.parseColor(bgHeaderColorHexa));

    }
    private  void applyHeaderStyleDarkMode(LinearLayoutCompat headerContainer){
        DarkModeModel darkModeModel=checkoutPageConfigModel.getAppearance().getDarkMode();

        String bgHeaderColor=darkModeModel.getLabelBackgroundColor();
        String bgHeaderColorHexa=ConvertColorHexa.convertHex(bgHeaderColor);
        headerContainer.setBackgroundColor(Color.parseColor(bgHeaderColorHexa));

    }



    private void updateItemFont(Context context){
//      SetFont font=new SetFont();
        Typeface bankNameTypeface= ResourcesCompat.getFont(context, R.font.roboto_medium);
        textBankName.setTypeface(bankNameTypeface);
        textBankName.setTextSize(12);
        textBankName.setPaintFlags(Paint.FAKE_BOLD_TEXT_FLAG);

    }

    private void postAddToFavorite(String bankId,boolean isFavorite){
        AddToFavoriteRequestModel model=new AddToFavoriteRequestModel(tranasctionId,bankId,isFavorite);
        Call<BaseResponse<AddToFavoriteModel>> call= RetrofitClient.getInstance().
                getApiClient().postAddToFavorite(Constant.CONTENT_TYPE,Constant.TOKEN,refererKey,model);

        call.enqueue(new Callback<BaseResponse<AddToFavoriteModel>>() {
            @Override
            public void onResponse(Call<BaseResponse<AddToFavoriteModel>> call, Response<BaseResponse<AddToFavoriteModel>> response) {
//                response.body();
            }

            @Override
            public void onFailure(Call<BaseResponse<AddToFavoriteModel>> call, Throwable t) {

            }
        });
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater=LayoutInflater.from(parent.getContext());
        if (viewType==VIEW_HEADER_TYPE){
            View view=inflater.inflate(R.layout.section_header_layout,parent,false);
            textSectionHeader=view.findViewById(R.id.text_section_header);
            sectionContainer=view.findViewById(R.id.section_header_container);

            //applyStyle
            if(isLightMode){
                applyHeaderStyleLightMode(sectionContainer);
            }else {
                applyHeaderStyleDarkMode(sectionContainer);
            }


            //Update Font
            updateHeaderFont(view.getContext(),textSectionHeader);



            return new SectionViewHolder(view);
        }else {
            View view=inflater.inflate(R.layout.card_payment_method_item_layout,parent,false);
            initItemView(view);
            //Update Font
            updateItemFont(view.getContext());

            //Update Image Favorite
            return new BankItemViewHolder(view);
        }

    }
    @Override
    public int getItemViewType(int position) {
        int currentPos = 0;

        for (BankPaymentMethodModel section : bankPaymentMethodModelList) {
            // Check if the current position corresponds to the section itself
            if (currentPos == position) {
                return VIEW_HEADER_TYPE;
            }
            // Increment the position to account for the section
            currentPos++;
            // Check if the current position corresponds to an item within the section
            if (section.getItems() != null) {
                for (BankPaymentMethodItemModel item : section.getItems()) {
                    if (currentPos == position) {
                        return VIEW_ITEM_TYPE;
                    }
                    currentPos++;
                }
            }
        }
        throw new IllegalArgumentException("InValid Position");
           }
    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, @SuppressLint("RecyclerView") int position) {
        int currentPos = 0;
        for (BankPaymentMethodModel section : bankPaymentMethodModelList) {
            if (currentPos == position) {
                // Bind data for section
                ((SectionViewHolder) holder).bindSection(section);

               // applyHeaderStyle(((SectionViewHolder) holder).headerContainer);
                return; // Exit the method after binding the section
            }
            currentPos++;

            if (section.getItems() != null) {
                for (BankPaymentMethodItemModel item : section.getItems()) {
                    if (currentPos == position) {
                        // Bind data for item
                        ((BankItemViewHolder) holder).bindItem(item);

                        //Set Item Click Event
                        holder.itemView.setOnClickListener(v->{
                            //Toast.makeText(v.getContext(), ""+item.getId(), Toast.LENGTH_SHORT).show();
                            listener.OnItemPaymentMethodClick(item);
                        });

                        //Set Favorite Click
                        ((BankItemViewHolder)holder).addToFavoriteContainer.setOnClickListener(v->{
                            item.setFavorite(!item.isFavorite());

                            boolean isFav=item.isFavorite();

                            //todo handle add to favorite with api
                            if(isFav){
                                //Toast.makeText(v.getContext(), ""+isFav, Toast.LENGTH_SHORT).show();
                                 postAddToFavorite(item.getId(),isFav);
                                ((BankItemViewHolder)holder).imageFavIcon.setImageResource(R.drawable.favorite_icon);

                            }else {
                                //Toast.makeText(v.getContext(), ""+isFav, Toast.LENGTH_SHORT).show();
                                postAddToFavorite(item.getId(),isFav);
                                ((BankItemViewHolder)holder).imageFavIcon.setImageResource(R.drawable.un_favorite_icon);
                            }

                        });

                        return; // Exit the method after binding the item
                    }
                    currentPos++;
                }
            }
        }
   }


    @Override
    public int getItemCount()
    {
        int count=0;
        for(BankPaymentMethodModel section:bankPaymentMethodModelList){
            count++;
            if(section.getSection()!=null){
                count+=section.getItems().size();
            }
        }
        return count;
    }

    @Override
    public int getHeaderPositionForItem(int itemPosition) {
       int headerPosition=0;
        do{
            if(this.isHeader(itemPosition)){
                headerPosition=itemPosition;
                break;
            }
            itemPosition-=1;
        }while (itemPosition>=0);
        return headerPosition;
    }

    @Override
    public boolean isHeader(int itemPosition) {
        int currentPos = 0;

        for (BankPaymentMethodModel section : bankPaymentMethodModelList) {
            if (itemPosition == currentPos) {
                return true; // This item is a section header
            }
            currentPos++; // Move to the next position
            if (section.getItems() != null) {
                // Check if the current itemPosition is within the range of this section's items
                if (itemPosition < currentPos + section.getItems().size()) {
                    return false; // This item is not a section header
                }
                // Increment currentPos to move to the next section
                currentPos += section.getItems().size();
            }
        }
        return false;

    }

    @Override
    public int getHeaderLayout(int headerPosition) {
       return R.layout.section_header_layout;
    }

    @Override
    public void bindHeaderData(View header, int headerPosition) {
        int currentPos = 0;
        LinearLayoutCompat sectionHeader=header.findViewById(R.id.section_header_container);
        AppCompatTextView textSectionHeader = header.findViewById(R.id.text_section_header);
        for (BankPaymentMethodModel section : bankPaymentMethodModelList) {
            if (currentPos == headerPosition) {
                if (language.equals(LanguageCode.EN)) {
                    textSectionHeader.setText(section.getSection());
                } else {
                    textSectionHeader.setText(section.getSectionKh());
                }
                updateHeaderFont(header.getContext(),textSectionHeader);

                if(isLightMode){
                    applyHeaderStyleLightMode(sectionHeader);//apply style

                }else {
                    applyHeaderStyleDarkMode(sectionHeader);
                }


                return;
            }
            currentPos++; // Move to the next position
            if (section.getItems() != null) {
                int itemCount = section.getItems().size();
                if (headerPosition < currentPos + itemCount) {
                    // This is not a header; it's within the items of the current section
                    return;
                }
                currentPos += itemCount;
            }
        }

    }


    public class SectionViewHolder extends RecyclerView.ViewHolder {

        private LinearLayoutCompat headerContainer;
        private AppCompatTextView textSection;
        public SectionViewHolder(@NonNull View itemView) {
            super(itemView);
            textSection=itemView.findViewById(R.id.text_section_header);
            headerContainer=itemView.findViewById(R.id.section_header_container);

            if(isLightMode){
                applyHeaderStyleLightMode();

            }else {
                applyHeaderStyleDarkMode();
            }
        }


         void applyHeaderStyleLightMode(){

            LightModeModel lightModeModel=checkoutPageConfigModel.getAppearance().getLightMode();

            String bgHeaderColor=lightModeModel.getLabelBackgroundColor();
            String bgHeaderColorHexa=ConvertColorHexa.convertHex(bgHeaderColor);
            headerContainer.setBackgroundColor(Color.parseColor(bgHeaderColorHexa));

        }
        void applyHeaderStyleDarkMode(){

            DarkModeModel darkModeModel=checkoutPageConfigModel.getAppearance().getDarkMode();

            String bgHeaderColor=darkModeModel.getLabelBackgroundColor();
            String bgHeaderColorHexa=ConvertColorHexa.convertHex(bgHeaderColor);
            headerContainer.setBackgroundColor(Color.parseColor(bgHeaderColorHexa));

        }
        void bindSection(BankPaymentMethodModel section){
            if(language.equals(LanguageCode.EN)){
                textSection.setText(section.getSection());
            }else {
                textSection.setText(section.getSectionKh());
            }

        }
    }

   public  class  BankItemViewHolder extends RecyclerView.ViewHolder {
        private AppCompatTextView textBankName,textBankServicePayment_Amount,
                textBankServiceCurrency,textSemicolon,textBankFee;
        private AppCompatImageView imageFavIcon, imageBankIcon;
        private FrameLayout addToFavoriteContainer,containerBankItemFirstLayer,
                containerBankItemSecondLayer;
        private LinearLayoutCompat containerFee;

        public BankItemViewHolder(@NonNull View itemView) {
            super(itemView);

            textBankName=itemView.findViewById(R.id.text_bank_name);
            textBankServicePayment_Amount=itemView.findViewById(R.id.text_payment_service_amount);
            imageFavIcon=itemView.findViewById(R.id.image_favorite_icon);
            imageBankIcon =itemView.findViewById(R.id.image_bank_icon);
            addToFavoriteContainer=itemView.findViewById(R.id.add_to_favorite_container);
            textBankServiceCurrency=itemView.findViewById(R.id.text_payment_service_currency);
            containerFee=itemView.findViewById(R.id.container_fee);
            textSemicolon=itemView.findViewById(R.id.text_semicolon);
            textBankFee=itemView.findViewById(R.id.text_payment_service_title);
            containerBankItemFirstLayer=itemView.findViewById(R.id.container_bank_item_first_layer);
            containerBankItemSecondLayer=itemView.findViewById(R.id.container_bank_item_second_layer);



            if(isLightMode){
                applyStyleShapeLightMode();

            }else {
                applyStyleShapeDarkMode();
            }



        }



        void applyStyleShapeLightMode(){

            LightModeModel lightModeModel=checkoutPageConfigModel.getAppearance().getLightMode();

//            card bank item
            String bgBankItemColor=lightModeModel.getButton().getBankButton().getBackgroundColor();
            String convertBgBankItemColor= ConvertColorHexa.convertHex(bgBankItemColor);

            ShapeDrawable bankItemShape=CustomShape.applyShape(Color.parseColor(convertBgBankItemColor),30);


            String selectColor=ConvertColorHexa.getFiftyPercentColor(bgBankItemColor);

            ShapeDrawable bankItemSelected=CustomShape.applyShape(Color.parseColor(selectColor),30);


            ShapeDrawable normal=new ShapeDrawable();
            normal.getPaint().setColor(Color.TRANSPARENT);

            StateListDrawable selector = new StateListDrawable();
            selector.addState(new int[]{android.R.attr.state_pressed}, bankItemSelected); // Selected state
            selector.addState(new int[]{}, normal);
            containerBankItemSecondLayer.setBackground(selector);

            containerBankItemFirstLayer.setElevation(35.0f);
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.P) {
                int shadow =Color.argb(36,183,190,203);
                containerBankItemFirstLayer.setOutlineSpotShadowColor(shadow);
            }
            containerBankItemFirstLayer.setBackground(bankItemShape);



         //bank name
            String bankNameColor=lightModeModel.getButton().getBankButton().getTextPrimary();
            String convertBankNameColor=ConvertColorHexa.convertHex(bankNameColor);
            textBankName.setTextColor(Color.parseColor(convertBankNameColor));

            //bank fee
            String bankFeeColor=lightModeModel.getButton().getBankButton().getTextSecondary();
            String convertBankFeeColor=ConvertColorHexa.convertHex(bankFeeColor);
            textBankFee.setTextColor(Color.parseColor(convertBankFeeColor));
            textBankServicePayment_Amount.setTextColor(Color.parseColor(convertBankFeeColor));
            textSemicolon.setTextColor(Color.parseColor(convertBankFeeColor));
            textBankServiceCurrency.setTextColor(Color.parseColor(convertBankFeeColor));



        //favorite background
            String bgFavButton=lightModeModel.getButton().getFavoriteButton().getBackgroundColor();
            String convertBgFavButton=ConvertColorHexa.convertHex(bgFavButton);
            ShapeDrawable favButton=CustomShape.applyShape(Color.parseColor(convertBgFavButton),20);

            String favButtonSelected=ConvertColorHexa.getFiftyPercentColor(bgFavButton);
            ShapeDrawable favButtonSelectColor=CustomShape.applyShape(Color.parseColor(favButtonSelected),20);
            StateListDrawable favSelector=SelectedState.selectedSate(favButton,favButtonSelectColor);
            addToFavoriteContainer.setBackground(favSelector);

        //favorite icon
            String faviconColor=lightModeModel.getButton().getFavoriteButton().getTextColor();
            String convertFaviconColor=ConvertColorHexa.convertHex(faviconColor);
            ColorFilter colorFilterFavicon=new PorterDuffColorFilter(Color.parseColor(convertFaviconColor), PorterDuff.Mode.SRC_ATOP);
            imageFavIcon.setColorFilter(colorFilterFavicon);

        }

       void applyStyleShapeDarkMode(){

           DarkModeModel darkModeModel=checkoutPageConfigModel.getAppearance().getDarkMode();

           //card bank item
           String bgBankItemColor=darkModeModel.getButton().getBankButton().getBackgroundColor();
           String convertBgBankItemColor= ConvertColorHexa.convertHex(bgBankItemColor);

           ShapeDrawable bankItemShape=CustomShape.applyShape(Color.parseColor(convertBgBankItemColor),30);


           String selectColor=ConvertColorHexa.getFiftyPercentColor(bgBankItemColor);

           ShapeDrawable bankItemSelected=CustomShape.applyShape(Color.parseColor(selectColor),30);


           ShapeDrawable normal=new ShapeDrawable();
           normal.getPaint().setColor(Color.TRANSPARENT);

           StateListDrawable selector = new StateListDrawable();
           selector.addState(new int[]{android.R.attr.state_pressed}, bankItemSelected); // Selected state
           selector.addState(new int[]{}, normal);
           containerBankItemSecondLayer.setBackground(selector);

           containerBankItemFirstLayer.setElevation(35.0f);
           if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.P) {
               int shadow =Color.argb(36,183,190,203);
               containerBankItemFirstLayer.setOutlineSpotShadowColor(shadow);
           }
           containerBankItemFirstLayer.setBackground(bankItemShape);



           //bank name
           String bankNameColor=darkModeModel.getButton().getBankButton().getTextPrimary();
           String convertBankNameColor=ConvertColorHexa.convertHex(bankNameColor);
           textBankName.setTextColor(Color.parseColor(convertBankNameColor));

           //bank fee
           String bankFeeColor=darkModeModel.getButton().getBankButton().getTextSecondary();
           String convertBankFeeColor=ConvertColorHexa.convertHex(bankFeeColor);
           textBankFee.setTextColor(Color.parseColor(convertBankFeeColor));
           textBankServicePayment_Amount.setTextColor(Color.parseColor(convertBankFeeColor));
           textSemicolon.setTextColor(Color.parseColor(convertBankFeeColor));
           textBankServiceCurrency.setTextColor(Color.parseColor(convertBankFeeColor));



           //favorite background
           String bgFavButton=darkModeModel.getButton().getFavoriteButton().getBackgroundColor();
           String convertBgFavButton=ConvertColorHexa.convertHex(bgFavButton);
           ShapeDrawable favButton=CustomShape.applyShape(Color.parseColor(convertBgFavButton),20);
           String favButtonSelected=ConvertColorHexa.getFiftyPercentColor(bgFavButton);
           ShapeDrawable favButtonSelectColor=CustomShape.applyShape(Color.parseColor(favButtonSelected),20);

           StateListDrawable favSelector=SelectedState.selectedSate(favButton,favButtonSelectColor);

           addToFavoriteContainer.setBackground(favSelector);

           //favorite icon
           String faviconColor=darkModeModel.getButton().getFavoriteButton().getTextColor();
           String convertFaviconColor=ConvertColorHexa.convertHex(faviconColor);
           ColorFilter colorFilterFavicon=new PorterDuffColorFilter(Color.parseColor(convertFaviconColor), PorterDuff.Mode.SRC_ATOP);
           imageFavIcon.setColorFilter(colorFilterFavicon);

       }
        @SuppressLint("SetTextI18n")
        void bindItem(BankPaymentMethodItemModel bankPaymentMethodItemModel){
            if(language.equals(LanguageCode.EN)){
                textBankName.setText(bankPaymentMethodItemModel.getName());
            }else {
                textBankName.setText(bankPaymentMethodItemModel.getNameKh());
            }

            textBankServicePayment_Amount.setText(formatCurrency(bankPaymentMethodItemModel.getFee(),transactionInfoModel.getCurrency()));


            if(transactionInfoModel.getCurrency().equals(CurrencyCode.USD)){
                textBankServiceCurrency.setText(CurrencyCode.USD);
            }else {
                textBankServiceCurrency.setText(CurrencyCode.KHR);
            }
            Picasso.get().load(bankPaymentMethodItemModel.getLogo())
                    .placeholder(R.drawable.placeholder_image)
                    .into(imageBankIcon) ;

            if(transactionInfoModel.isAllowFavorite()){
                addToFavoriteContainer.setVisibility(View.VISIBLE);
                if(bankPaymentMethodItemModel.isFavorite()){
                    imageFavIcon.setImageResource(R.drawable.favorite_icon);
                }else {
                    imageFavIcon.setImageResource(R.drawable.un_favorite_icon);
                }
            }else {
                addToFavoriteContainer.setVisibility(View.GONE);
            }

            if(checkoutPageConfigModel.getSetting().isDisplayFee()){
                containerFee.setVisibility(View.VISIBLE);
            }else {
                containerFee.setVisibility(View.GONE);
            }

        }
    }

    public interface PaymentMethodClickListener{
       void OnItemPaymentMethodClick(BankPaymentMethodItemModel id);
    }

    private String formatCurrency(double amount,String currency){
        NumberFormat currencyFormat=NumberFormat.getNumberInstance();
        if(currency.equals(CurrencyCode.KHR)){
            return currencyFormat.format(amount);
        }else {
            DecimalFormat decimalFormat=new DecimalFormat("#,##0.00");
            return  decimalFormat.format(amount);
        }

    }
}
