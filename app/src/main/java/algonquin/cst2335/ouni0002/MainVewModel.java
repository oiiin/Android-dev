package algonquin.cst2335.ouni0002;

import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

class MainViewModel extends ViewModel {
    public MutableLiveData<String> editString = new MutableLiveData<>();
    public MutableLiveData<Boolean> isSelected =new MutableLiveData<>();
}
