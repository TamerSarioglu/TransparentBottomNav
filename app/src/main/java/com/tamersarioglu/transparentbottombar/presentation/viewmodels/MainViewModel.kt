import androidx.lifecycle.ViewModel
import com.tamersarioglu.transparentbottombar.presentation.navigation.NavRoutes
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow

class MainViewModel : ViewModel() {
    private val _currentRoute = MutableStateFlow(NavRoutes.HOME)
    val currentRoute = _currentRoute.asStateFlow()

    fun updateRoute(route: String) {
        _currentRoute.value = route
    }
} 