package com.bill24.bill24onlinepaymentsdk.adapter;

import android.annotation.SuppressLint;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;

import androidx.annotation.NonNull;
import androidx.appcompat.widget.AppCompatImageView;
import androidx.appcompat.widget.AppCompatTextView;
import androidx.appcompat.widget.LinearLayoutCompat;
import androidx.recyclerview.widget.RecyclerView;

import com.bill24.bill24onlinepaymentsdk.R;
import com.bill24.bill24onlinepaymentsdk.model.BankPaymentMethodItemModel;
import com.bill24.bill24onlinepaymentsdk.model.BankPaymentMethodModel;
import com.squareup.picasso.Picasso;

import java.util.List;

public class PaymentMethodAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    private static final int VIEW_HEADER_TYPE=0;
    private static final int VIEW_ITEM_TYPE=1;
    private List<BankPaymentMethodModel> bankPaymentMethodModelList;
    private PaymentMethodClickListener listener;

   public void setPaymentMethod(List<BankPaymentMethodModel> bankPaymentMethodModelList){
       this.bankPaymentMethodModelList=bankPaymentMethodModelList;
       notifyDataSetChanged();
   }
    public void setOnItemClickListener(PaymentMethodClickListener listener){
        this.listener=listener;
    }



    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater=LayoutInflater.from(parent.getContext());
        if (viewType==VIEW_HEADER_TYPE){
            View view=inflater.inflate(R.layout.section_header_layout,parent,false);
            return new SectionViewHolder(view);
        }else {
            View view=inflater.inflate(R.layout.card_payment_method_item_layout,parent,false);
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
  //OLD CODE
        //       return position ==0 || position == bankPaymentMethodModelList.size() +1 ?VIEW_HEADER_TYPE:VIEW_ITEM_TYPE;

        //        int count=0;
        //        for (BankPaymentMethodModel section:bankPaymentMethodModelList){
        //            if(position == count){
        //
        //                return VIEW_HEADER_TYPE;
        //            }
        //            count++;
        //            if(section.getItems()!=null){
        //                int itemCount=section.getItems().size();
        //                count+=itemCount;
        //                if(position < count){
        //
        //                    return VIEW_ITEM_TYPE;
        //                }
        //            }
        //        }
        //        throw new IllegalArgumentException("InValid Position");
          //  }
    //END OLD CODE
    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, @SuppressLint("RecyclerView") int position) {
        int currentPos = 0;

        for (BankPaymentMethodModel section : bankPaymentMethodModelList) {
            if (currentPos == position) {
                // Bind data for section
                ((SectionViewHolder) holder).bind(section);
                return; // Exit the method after binding the section
            }

            currentPos++;

            if (section.getItems() != null) {
                for (BankPaymentMethodItemModel item : section.getItems()) {
                    if (currentPos == position) {
                        // Bind data for item
                        ((BankItemViewHolder) holder).bind(item);

                        //Set Item Click Event
                        ((BankItemViewHolder) holder).itemBankContainer.setOnClickListener(v->{
//
                            listener.OnItemPaymentMethodClick(item.getId());
                        });


                        return; // Exit the method after binding the item
                    }
                    currentPos++;
                }
            }
        }
   }

// OLD CODE

    //       int count=0;
    //        if(holder instanceof SectionViewHolder){
    //            SectionViewHolder sectionViewHolder=(SectionViewHolder) holder;
    ////            int sectionIndex=0;
    ////            int count=0;
    //            for(BankPaymentMethodModel section:bankPaymentMethodModelList){
    //                if(position==count){
    //                    sectionViewHolder.bind(section.getSectionKh());
    //                    return;
    //                }
    //                count++;
    //                if(section.getItems()!=null){
    //                    int itemCount=section.getItems().size();
    //                    count+=itemCount;
    //                    if(position<count){
    //                        return;
    //                    }
    //                }
    //                //sectionIndex++;
    //            }
    //        }else if(holder instanceof BankItemViewHolder){
    //            int adjustedPostion=position-1;
    //            for (BankPaymentMethodModel section:bankPaymentMethodModelList){
    //                if(section.getItems()!=null && adjustedPostion <section.getItems().size()){
    //                    BankItemViewHolder bankItemViewHolder=(BankItemViewHolder) holder;
    //                    bankItemViewHolder.bind(section.getItems().get(adjustedPostion));
    //                    //Set Event When Click On Item
    //
    //                    return;
    //                }
    //                if(section.getItems()!=null){
    //                    adjustedPostion-=section.getItems().size();
    //
    //                }
    //            }
    //        }

   // }
    // END OLD CODE



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


    public class SectionViewHolder extends RecyclerView.ViewHolder {
        private AppCompatTextView textSection;
        public SectionViewHolder(@NonNull View itemView) {
            super(itemView);
            textSection=itemView.findViewById(R.id.text_section_header);
        }
        void bind(BankPaymentMethodModel section){
            textSection.setText(section.getSection());
        }
    }

   public  class  BankItemViewHolder extends RecyclerView.ViewHolder {
        private AppCompatTextView textBankName,textBankServicePayment_Amount;
        private AppCompatImageView imageFavoriteIcon, imageBankIcon;
        private LinearLayoutCompat itemBankContainer;
        private FrameLayout addToFavorite;
        public BankItemViewHolder(@NonNull View itemView) {
            super(itemView);

            textBankName=itemView.findViewById(R.id.text_bank_name);
            textBankServicePayment_Amount=itemView.findViewById(R.id.text_payment_service_amount);
            imageFavoriteIcon=itemView.findViewById(R.id.image_favorite_icon);
            imageBankIcon =itemView.findViewById(R.id.image_bank_icon);
            itemBankContainer=itemView.findViewById(R.id.bank_name_icon_container);
            addToFavorite=itemView.findViewById(R.id.add_to_favorite_container);
//            itemBankContainer.setOnClickListener(v->{
//                Toast.makeText(v.getContext(),"container",Toast.LENGTH_SHORT).show();
//            });
//            addToFavorite.setOnClickListener(v->{
//                Toast.makeText(v.getContext(),"add to favorite",Toast.LENGTH_SHORT).show();
//            });
        }

        void bind(BankPaymentMethodItemModel bankPaymentMethodItemModel){
            textBankName.setText(bankPaymentMethodItemModel.getName());
            textBankServicePayment_Amount.setText(""+bankPaymentMethodItemModel.getFee());
            Picasso.get().load(bankPaymentMethodItemModel.getLogo()).into(imageBankIcon);
        }
    }
    public interface PaymentMethodClickListener{
       void OnItemPaymentMethodClick(String id);
       void OnFavoriteIconClick(int position);
    }
}
