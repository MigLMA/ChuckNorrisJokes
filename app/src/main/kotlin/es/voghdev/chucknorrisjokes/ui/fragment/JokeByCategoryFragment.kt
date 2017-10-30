package es.voghdev.chucknorrisjokes.ui.fragment

import android.os.Bundle
import es.voghdev.chucknorrisjokes.R
import es.voghdev.chucknorrisjokes.app.AndroidResLocator
import es.voghdev.chucknorrisjokes.app.configureDefaultAdapter
import es.voghdev.chucknorrisjokes.datasource.api.GetJokeCategoriesApiImpl
import es.voghdev.chucknorrisjokes.datasource.api.GetRandomJokeApiImpl
import es.voghdev.chucknorrisjokes.model.JokeCategory
import es.voghdev.chucknorrisjokes.repository.ChuckNorrisRepository
import es.voghdev.chucknorrisjokes.ui.presenter.JokeByCategoryPresenter
import kotlinx.android.synthetic.main.fragment_joke_by_category.*
import kotlinx.coroutines.experimental.runBlocking

class JokeByCategoryFragment : BaseFragment(), JokeByCategoryPresenter.MVPView, JokeByCategoryPresenter.Navigator {
    var presenter: JokeByCategoryPresenter? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        val chuckNorrisRepository = ChuckNorrisRepository(
                GetRandomJokeApiImpl(),
                GetJokeCategoriesApiImpl()
        )

        presenter = JokeByCategoryPresenter(AndroidResLocator(context), chuckNorrisRepository)
        presenter?.view = this
        presenter?.navigator = this

        runBlocking {
            presenter?.initialize()
        }
    }

    override fun getLayoutId(): Int {
        return R.layout.fragment_joke_by_category
    }

    override fun fillCategories(list: List<JokeCategory>) {
        spn_categories.configureDefaultAdapter(list.map { it.name })
    }
}
