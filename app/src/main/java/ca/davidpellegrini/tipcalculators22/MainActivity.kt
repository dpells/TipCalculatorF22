package ca.davidpellegrini.tipcalculators22

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.Button
import android.widget.RadioGroup
import android.widget.SeekBar
import android.widget.TextView
import ca.davidpellegrini.tipcalculators22.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity(), View.OnClickListener, SeekBar.OnSeekBarChangeListener,
    RadioGroup.OnCheckedChangeListener {

    lateinit var binding: ActivityMainBinding
    private var _tipPercent = 0.2f

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val decreaseTipButton = binding.decTipBtn
        decreaseTipButton.setOnClickListener(this)

        binding.incTipBtn.setOnClickListener(this)

        binding.tipPercentSb.setOnSeekBarChangeListener(this)

        binding.numPeopleGroup.setOnCheckedChangeListener(this)
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

        binding.tipPercentTv.setText((_tipPercent * 100).toInt().toString() + "%")
        binding.tipPercentSb.setProgress((_tipPercent * 100).toInt(), true)

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
            binding.tipPercentTv.setText(progress.toString() + "%")
            Log.d("SeekBar:OnProgressChanged", "Changing the value")
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
    }
}