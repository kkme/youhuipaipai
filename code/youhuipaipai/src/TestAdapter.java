import com.example.youhuipaipai.R;
import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.*;
import android.view.LayoutInflater;
import java.util.List;

public class TestAdapter extends BaseAdapter{
	
	private Context context;
	private List<Object> list;
	
	public TestAdapter(Context context,List<Object> list){
		this.context=context;
		this.list=list;
	}

	@Override
	public View getView(int position, View view, ViewGroup parent) {
		ItemView item=null;
		if(view==null){
			item=new ItemView();
			view=LayoutInflater.from(context).inflate(R.layout.imageadapter, null);
		    item.iv=(ImageView)view.findViewById(R.id.iv);

			view.setTag(item);
		}else{
			item=(ItemView)view.getTag();
		}
		
		Object value=list.get(position);
		
		return view;
	}
	
	class ItemView{
	 public ImageView  iv;
		
		
	}
	
	@Override
	public int getCount() {
		return list.size();
	}
	
	@Override
	public Object getItem(int position) {
		return list.get(position);
	}

	@Override
	public long getItemId(int position) {
		return position;
	}

}
