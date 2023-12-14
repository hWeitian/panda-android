package com.example.foodpanda_capstone.view.ui.screen

import android.util.Log
import android.widget.Space
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.example.foodpanda_capstone.view.ui.composable.InnerTopAppBar
import com.example.foodpanda_capstone.view.ui.composable.PrimaryButton
import com.example.foodpanda_capstone.viewmodel.PlaylistFormViewModel

@Composable
fun PlaylistFormScreen(navController: NavController) {
    val viewModel = PlaylistFormViewModel()

    InnerTopAppBar(title = "Generate Playlist", backBtnClick = { Log.i("Panda", "Back Btn Clicked") }) {

        Box(modifier = Modifier.fillMaxSize()) {
            Column (
                modifier = Modifier.fillMaxSize().padding(top = 10.dp),
                verticalArrangement = Arrangement.SpaceBetween) {
                Column {
                    CustomTextInputField("Cuisines", viewModel.cuisines)
                    CustomTextInputField("Num of Dishes", viewModel.numOfDish)
                    CustomTextInputField("Max Budget", viewModel.maxBudget)

                }
                PrimaryButton(name = "Generate ideas", null) {
                    Log.i("Panda", "Create ideas btn clicked")
                }
            }
        }
    }

}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun CustomTextInputField (label: String, inputState: MutableState<String>) {

    OutlinedTextField(value = inputState.value,
        onValueChange = {inputState.value = it},
        modifier = Modifier
            .fillMaxWidth()
            .padding(bottom = 10.dp),
        label = { Text(text = label) }
        )
}
