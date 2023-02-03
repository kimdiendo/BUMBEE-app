package com.example.bumbee.activities.note;

import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;
import com.example.bumbee.R;
import java.util.ArrayList;

public class NoteAdapter extends BaseAdapter {
    final ArrayList<VocabModel> list_vocab;

    public NoteAdapter(ArrayList<VocabModel> list_vocab) {
        this.list_vocab = list_vocab;
    }
    @Override
    //Hàm trả về kích thước của list_vocab
    public int getCount() {
        return list_vocab.size();
    }

    @Override
    //Hàm trả về đối tượng theo vị trí
    //list_vocab.get(position) trả về đối tượn hay phần tử trong arraylist tại vị trí đó.
    public Object getItem(int position) {
        return list_vocab.get(position);
    }

    @Override
    //Hàm trả về ID
    public long getItemId(int position) {
        return 0;
    }

    @Override
    //Hàm set View cho mỗi phần tử
    public View getView(int position, View convertView , ViewGroup parent)
    {
        View viewProduct;
        //convertView là View hiển thị phần tử , nếu là Null thì cần tạo mới.
        if (convertView == null) {
            viewProduct = View.inflate(parent.getContext(), R.layout.activity_detail_vocab, null);
        }
        else
            viewProduct = convertView;
        VocabModel product = (VocabModel) getItem(position);
        ((TextView) viewProduct.findViewById(R.id.vocab)).setText(product.Vocab);
        ((TextView) viewProduct.findViewById(R.id.idmeaning)).setText(product.Meaning);
        return viewProduct;
    }
}
