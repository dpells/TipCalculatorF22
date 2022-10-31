package ca.davidpellegrini.tipcalculators22

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.util.Log
import android.view.View
import android.widget.*
import androidx.core.widget.addTextChangedListener
import java.text.NumberFormat
import ca.davidpellegrini.tipcalculators22.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity(), View.OnClickListener, SeekBar.OnSeekBarChangeListener,
    RadioGroup.OnCheckedChangeListener, AdapterView.OnItemSelectedListener,
    AdapterView.OnItemClickListener, TextWatcher {

    lateinit var binding: ActivityMainBinding
    private var _tipPercent = 0.2f
    private val currentyFormat = NumberFormat.getCurrencyInstance()
    private val percentFormat = NumberFormat.getPercentInstance()


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val decreaseTipButton = binding.decTipBtn
        decreaseTipButton.setOnClickListener(this)

        binding.incTipBtn.setOnClickListener(this)
        binding.tipPercentSb.setOnSeekBarChangeListener(this)
        binding.numPeopleGroup.setOnCheckedChangeListener(this)
        binding.billAmountEt.addTextChangedListener(this)

        binding.tipPercentTv.text = percentFormat.format(_tipPercent)
        binding.tipPercentSb.progress = (_tipPercent * 100).toInt()

        val spinnerAdapter = ArrayAdapter<String>(this, android.R.layout.simple_spinner_dropdown_item)
        spinnerAdapter.add(getString(R.string.one_person_string))
        spinnerAdapter.add(getString(R.string.two_people_string))
        spinnerAdapter.add(getString(R.string.three_people_string))
        spinnerAdapter.add(getString(R.string.four_people_string))
        spinnerAdapter.add("Too many people to count")
        binding.numPeopleSpinner.adapter = spinnerAdapter
        // we don't click on things in a Spinner, we select them
        binding.numPeopleSpinner.onItemSelectedListener = this

        val listViewAdapter = ArrayAdapter<String>(this, android.R.layout.simple_list_item_multiple_choice)
        listViewAdapter.add("Onions")
        listViewAdapter.add("Peppers")
        listViewAdapter.add("Cheese")
        listViewAdapter.add("Guac")
        listViewAdapter.add("Beans")
        listViewAdapter.add("Chips")
        listViewAdapter.add("Salsa")
        listViewAdapter.add("Sour Cream")
        binding.listView.adapter = listViewAdapter
        binding.listView.onItemClickListener = this


//        binding.incTipBtn.setOnClickListener{
//            _tipPercent += 0.05f
//            Log.i("OnClickListener", "You've clicked a different button")
//        }

        /*
        val decreaseTipButton = findViewById<Button>(R.id.dec_tip_btn)
        decreaseTipButton.setOnClickListener(this)

        findViewById<Button>(R.id.inc_tip_btn).setOnClickListener {
            Log.i("OnClickListener", "You've clicked a different button")
        }
         */
    }

    /**
     * Called when a view has been clicked.
     *
     * @param v The view that was clicked.
     */

    override fun onClick(v: View?) {
        when(v?.id){
            R.id.inc_tip_btn -> {
                _tipPercent += 0.05f
                if(_tipPercent > 1f)
                    _tipPercent = 1f
            }
            R.id.dec_tip_btn -> {
                _tipPercent -= 0.05f
                if(_tipPercent < 0f)
                    _tipPercent = 0f
            }
        }

        binding.tipPercentSb.setProgress((_tipPercent * 100).toInt(), true)
        updateScreen()

        Log.i("Button", "You clicked decrease so the value will change")
    }

    /**
     * Notification that the progress level has changed. Clients can use the fromUser parameter
     * to distinguish user-initiated changes from those that occurred programmatically.
     *
     * @param seekBar The SeekBar whose progress has changed
     * @param progress The current progress level. This will be in the range min..max where min
     * and max were set by [ProgressBar.setMin] and
     * [ProgressBar.setMax], respectively. (The default values for
     * min is 0 and max is 100.)
     * @param fromUser True if the progress change was initiated by the user.
     */
    override fun onProgressChanged(seekBar: SeekBar?, progress: Int, fromUser: Boolean) {
        if(fromUser) {
            _tipPercent = progress / 100f
            updateScreen()
        }
    }

    /**
     * Notification that the user has started a touch gesture. Clients may want to use this
     * to disable advancing the seekbar.
     * @param seekBar The SeekBar in which the touch gesture began
     */
    override fun onStartTrackingTouch(seekBar: SeekBar?) {
        // TODO("Not yet implemented")
        // required method, nothing to do
    }

    /**
     * Notification that the user has finished a touch gesture. Clients may want to use this
     * to re-enable advancing the seekbar.
     * @param seekBar The SeekBar in which the touch gesture began
     */
    override fun onStopTrackingTouch(seekBar: SeekBar?) {
        // TODO("Not yet implemented")
        // required method, nothing to do
    }

    /**
     *
     * Called when the checked radio button has changed. When the
     * selection is cleared, checkedId is -1.
     *
     * @param group the group in which the checked radio button has changed
     * @param checkedId the unique identifier of the newly checked radio button
     */
    override fun onCheckedChanged(group: RadioGroup?, checkedId: Int) {
        if(checkedId == R.id.one_person_rb){
            binding.subTotalLl.setVisibility(View.GONE)
        }
        else{
            binding.subTotalLl.setVisibility(View.VISIBLE)
        }
        updateScreen()
    }

    /**
     *
     * Callback method to be invoked when an item in this view has been
     * selected. This callback is invoked only when the newly selected
     * position is different from the previously selected position or if
     * there was no selected item.
     *
     * Implementers can call getItemAtPosition(position) if they need to access the
     * data associated with the selected item.
     *
     * @param parent The AdapterView where the selection happened
     * @param view The view within the AdapterView that was clicked
     * @param position The position of the view in the adapter
     * @param id The row id of the item that is selected
     */
    override fun onItemSelected(parent: AdapterView<*>?, view: View?, position: Int, id: Long) {
//        val v: TextView?
//        if(view != null){
//            v = view as TextView
//        }
//        else v = null
//
//        if(v != null && v.text == getString(R.string.one_person_string)){
//            binding.subTotalLl.visibility = View.GONE
//        }
//        else{
//            binding.subTotalLl.visibility = View.VISIBLE
//        }
        if(position == 0){
            binding.subTotalLl.visibility = View.GONE
        }
        else{
            binding.subTotalLl.visibility = View.VISIBLE
        }
    }

    /**
     * Callback method to be invoked when the selection disappears from this
     * view. The selection can disappear for instance when touch is activated
     * or when the adapter becomes empty.
     *
     * @param parent The AdapterView that now contains no selected item.
     */
    override fun onNothingSelected(parent: AdapterView<*>?) {
        // TODO("Not yet implemented")
    }

    /**
     * Callback method to be invoked when an item in this AdapterView has
     * been clicked.
     *
     *
     * Implementers can call getItemAtPosition(position) if they need
     * to access the data associated with the selected item.
     *
     * @param parent The AdapterView where the click happened.
     * @param view The view within the AdapterView that was clicked (this
     * will be a view provided by the adapter)
     * @param position The position of the view in the adapter.
     * @param id The row id of the item that was clicked.
     */
    override fun onItemClick(parent: AdapterView<*>?, view: View?, position: Int, id: Long) {
        val checkedTextView = view as CheckedTextView

        checkedTextView.isChecked = !checkedTextView.isChecked
    }

    private fun updateScreen(){
        // how much is the bill
        val billAmountString = binding.billAmountEt.text.toString()
        var billAmount = 0f;
        if(billAmountString.isNotEmpty() && billAmountString.isNotBlank()){
            billAmount = billAmountString.toFloat()
        }
        // calculate the tip amount
        val tipAmount = billAmount * _tipPercent
        // calculate the subtotal
        val subtotal = tipAmount + billAmount
        // calculate the total (with num people)
        var numPeople = 2
        //when(binding.numPeopleSpinner.selectedItemPosition)
        when(binding.numPeopleGroup.checkedRadioButtonId){
            R.id.one_person_rb -> numPeople = 1
            R.id.two_people_rb -> numPeople = 2
            R.id.three_people_rb -> numPeople = 3
            R.id.four_people_rb -> numPeople = 4
            else -> numPeople = 2
        }
        val totalAmount = subtotal / numPeople

        binding.tipPercentTv.text = percentFormat.format(_tipPercent)
        binding.tipAmountTV.text = currentyFormat.format(tipAmount)
        binding.subtotalTv.text = currentyFormat.format(subtotal)
        binding.totalAmountTv.text = currentyFormat.format(totalAmount)
    }

    /**
     * This method is called to notify you that, within `s`,
     * the `count` characters beginning at `start`
     * are about to be replaced by new text with length `after`.
     * It is an error to attempt to make changes to `s` from
     * this callback.
     */
    override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {
        // TODO("Not yet implemented")
    }

    /**
     * This method is called to notify you that, within `s`,
     * the `count` characters beginning at `start`
     * have just replaced old text that had length `before`.
     * It is an error to attempt to make changes to `s` from
     * this callback.
     */
    override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
        updateScreen()
    }

    /**
     * This method is called to notify you that, somewhere within
     * `s`, the text has been changed.
     * It is legitimate to make further changes to `s` from
     * this callback, but be careful not to get yourself into an infinite
     * loop, because any changes you make will cause this method to be
     * called again recursively.
     * (You are not told where the change took place because other
     * afterTextChanged() methods may already have made other changes
     * and invalidated the offsets.  But if you need to know here,
     * you can use [Spannable.setSpan] in [.onTextChanged]
     * to mark your place and then look up from here where the span
     * ended up.
     */
    override fun afterTextChanged(s: Editable?) {
        // TODO("Not yet implemented")
    }
}