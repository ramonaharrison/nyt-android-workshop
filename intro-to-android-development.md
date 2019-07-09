# Android Development Workshop

---

# Series overview

- Intro to Android Development (Monday 2 - 4 pm)
- Kotlin for Android Development (Tuesday 2 - 4 pm)
- Advanced Android (Wednesday 2 - 4 pm)

*Office hours from 4 - 5 pm all three days.*

---

# Ramona Harrison


- Android Engineer on App Platforms :rocket:
- Native Audio, AR, Home
- Sits on 2S

![left](http://deckset-assets.s3-website-us-east-1.amazonaws.com/colnago1.jpg)

---

# Akasha Archer


![left](http://deckset-assets.s3-website-us-east-1.amazonaws.com/colnago1.jpg)

---

# Himadri Narasimhamurthy

# Amine Toualbi


![right](http://deckset-assets.s3-website-us-east-1.amazonaws.com/colnago1.jpg)

---

# Format
- Live coding
- Ask questions!
- We'll be available here from 4 - 5 pm

---

# Remote Participants
- Remote working groups
- Ask questions!
- We'll stay dialed in from 4 - 5 pm

---

# Resources

- Join **#learn-android-2019** on NYTimes Slack
- Resources at **github.com/ramonaharrison/nyt-android-workshop**

---

# The Goal

Build a NYTimes app using the [public API](https://developer.nytimes.com/).

---

# Creating a project
- Open Android Studio
- "Start a new Android Studio project"
- "Empty Activity"

---

# Configure the project

![inline](images/configure-the-project.png)

---

# Getting around Android Studio
- Project panel
- Run button
- Emulator

---

# Edit the layout
- Open activity_main.xml
- Find the text that says "Hello World!"
- Replace it with your own message and run the app

---

# Displaying a list
- From activity_main.xml, open the "Design" tab
- Delete the `TextView`
- Drag to add a `RecyclerView`
- Give it an id: `recycler_view`

---

# Creating an adapter

In a new file NewsAdapter.kt

```kotlin
class NewsAdapter : RecyclerView.Adapter<NewsAdapter.NewsViewHolder>() {

    class NewsViewHolder(val headline: TextView) : RecyclerView.ViewHolder(headline)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): NewsViewHolder {

    }

    override fun getItemCount(): Int {

    }

    override fun onBindViewHolder(holder: NewsViewHolder, position: Int) {

    }
}
```

---

# Create a viewholder with a view to display the headline

In NewsAdapter.kt

```kotlin
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): NewsViewHolder {
        val headlineTextView = TextView(parent.context)
        return NewsViewHolder(headlineTextView)
    }

```

---

# Create a list of data to bind with the adapter


In NewsAdapter.kt

```kotlin
class NewsAdapter : RecyclerView.Adapter<NewsAdapter.NewsViewHolder>() {

    var news: List<String> = ArrayList()
    //...
}
```

---

# We want to display as many headline items as there are news items

In NewsAdapter.kt

```kotlin
    override fun getItemCount(): Int {
        return news.size
    }
```

---

# When an item is scrolled onto the screen, show the headline

In NewsAdapter.kt

```kotlin
    override fun onBindViewHolder(holder: NewsViewHolder, position: Int) {
        holder.headline.text = news[position]
    }
```

---

# Setting up the RecyclerView

In MainActivity.kt

```kotlin
    lateinit var recyclerView: RecyclerView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        recyclerView = findViewById(R.id.recycler_view)
        recyclerView.layoutManager = LinearLayoutManager(this)
        recyclerView.adapter = NewsAdapter()
    }
```

---

# Allow the news to be set from outside of the adapter

In NewsAdapter.kt

```kotlin
    var news: List<String> = emptyList()

    fun updateNews(news: List<String>) {
        this.news = news
        notifyDataSetChanged()
    }
```

In MainActivity.kt

```kotlin
    val adapter = NewsAdapter()

    val news = listOf(
               "Crocodiles Went Through a Vegetarian Phase, Too", 
               "This Cockatoo Thinks He Can Dance", 
               "The Moon Is a Hazardous Place to Live")

    override fun onCreate(savedInstanceState: Bundle?) {
        // ...
        recyclerView.adapter = adapter

        adapter.updateNews(news)
    }
```

---

# Make a nicer layout

- Create a new layout file `item_news.xml`
- Add an ImageView and two TextViews and give them `id` values
- Adjust the constraints and margins to align the views
- Give the ImageView a placeholder background

![70% right](images/nicer-layout.png)

---

# Inflate the layout in the adapter

In NewsAdapter.kt

```kotlin
class NewsAdapter(val context: Context) : RecyclerView.Adapter<NewsAdapter.NewsViewHolder>() {

	class NewsViewHolder(view: View) : RecyclerView.ViewHolder(view)

    val inflater = LayoutInflater.from(context)

    //...

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): NewsViewHolder {
        val view = inflater.inflate(R.layout.item_news, parent, false)
        return NewsViewHolder(view)
    }

```

---

# Bind the data to the new layout


```kotlin
    override fun onBindViewHolder(holder: NewsViewHolder, position: Int) {
        holder.itemView.headline.text = news[position]
    }
```

---

# Create a data class to represent a news story

Create a new file NewsStory.kt


```kotlin
data class NewsStory(
    val headline: String,
    val summary: String,
    val imageUrl: String,
    val clickUrl: String
)
```

---

# Update the adapter to handle the new data class

In NewsAdapter.kt

```kotlin
    var news: List<NewsStory> = ArrayList()

    fun updateNews(news: List<NewsStory>) {
        this.news = news
        notifyDataSetChanged()
    }

```

---

# Bind the new data to the new layout

In NewsAdapter.kt

```kotlin
    override fun onBindViewHolder(holder: NewsViewHolder, position: Int) {
        holder.itemView.headline.text = news[position].headline
        holder.itemView.summary.text = news[position].summary
    }
```

---

# Update the data being passed to the adapter

In MainActivity.kt

```kotlin
   override fun onCreate(savedInstanceState: Bundle?) {
        //...

        updateNews()
    }

    fun updateNews() {
        val news = listOf(
            NewsStory(
                headline = "Crocodiles Went Through a Vegetarian Phase, Too",
                summary = "Ancestors of modern crocodiles evolved to survive on a plant diet at least three times, researchers say.",
                imageUrl = "https://static01.nyt.com/images/2019/07/09/science/27TB-VEGGIECROC1/27TB-VEGGIECROC1-videoLarge.jpg",
                clickUrl = "https://www.nytimes.com/2019/06/27/science/crocodiles-vegetarian-teeth.html"),

            NewsStory(
                headline = "This Cockatoo Thinks He Can Dance",
                summary = "Researchers have become convinced that Snowball, a YouTube sensation, and perhaps other animals, share humans’ sensitivity to music.",
                imageUrl = "https://static01.nyt.com/images/2019/07/09/autossell/Screen-Shot-2019-07-09-at-1/Screen-Shot-2019-07-09-at-1-videoLarge.jpg",
                clickUrl = "https://www.nytimes.com/2019/07/09/science/cockatoo-snowball-dance.html"),

            NewsStory(
                headline = "The Moon Is a Hazardous Place to Live",
                summary = "If we get back to the lunar surface, astronauts will have to contend with much more than perilous rocket flights and the vacuum of space.",
                imageUrl = "https://static01.nyt.com/images/2019/07/14/science/14MOONHAZARDS4/14MOONHAZARDS4-mediumThreeByTwo440-v2.jpg",
                clickUrl = "https://www.nytimes.com/2019/07/08/science/apollo-moon-colony-dangers.html")
        )
        adapter.updateNews(news)
    }

```

---

# Load the image URL in the ImageView

We'll use a third-party image loading library called [Picasso](https://square.github.io/picasso/).

---

# Add the Picasso dependency

In app/build.gradle

```groovy
dependencies {
	//...
	implementation 'com.squareup.picasso:picasso:2.71828'
}

```

Then do a Gradle sync.

---

# Load the image URL in the ImageView

In NewsAdapter.kt


```kotlin
    override fun onBindViewHolder(holder: NewsViewHolder, position: Int) {
        //...

        Picasso.get().load(news[position].imageUrl).into(holder.itemView.imageView)
    }
```

---

# Make a toast when a story is clicked

In NewsAdapter.kt

```kotlin
    override fun onBindViewHolder(holder: NewsViewHolder, position: Int) {
        //...

        holder.itemView.setOnClickListener {
            Toast.makeText(context, news[position].headline, Toast.LENGTH_LONG).show()
        }
    }
```

---






