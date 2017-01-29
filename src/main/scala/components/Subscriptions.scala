package lt.dvim.pocaboard.components

import japgolly.scalajs.react._
import japgolly.scalajs.react.vdom.prefix_<^._
import japgolly.scalajs.react.{BackendScope, ReactComponentB}
import lt.dvim.pocaboard.PocketCastsApi.Podcast
import upickle.default._

import scala.annotation.tailrec
import scala.util.Random

case class Layout(items: Int, idx: Seq[Int])
object Layout {

  val EightFour = Layout(2, Seq(8, 4))
  val FourEight = Layout(2, Seq(4, 8))
  val SixSix = Layout(2, Seq(6, 6))
  val Twelve = Layout(1, Seq(12))

  def randomLayout(rnd: Random) = rnd.nextInt(100) match {
    case x if x > 70 => EightFour
    case x if x > 40 => FourEight
    case x if x > 10 => SixSix
    case _ => Twelve
  }

  def generateLayout(itemCount: Int, rnd: Random) = {

    @tailrec
    def gen(cnt: Int, layout: Seq[Layout]): Seq[Layout] = cnt match {
      case 0 => layout
      case 1 => layout :+ Twelve
      case c =>
        val l = randomLayout(rnd)
        gen(cnt - l.items, layout :+ l)
    }

    gen(itemCount, Seq.empty)
  }
}

object Subscriptions {

  case class State(email: String, podcasts: Seq[Podcast])

  val allJson = """[{"id":119276,"uuid":"052df5e0-72b8-012f-1d57-525400c11844","url":"http://www.npr.org/programs/ted-radio-hour/","title":"TED Radio Hour","description":"The TED Radio Hour is a journey through fascinating ideas: astonishing inventions, fresh approaches to old problems, new ways to think and create. Based on Talks given by riveting speakers on the world-renowned TED stage, each show is centered on a common theme – such as the source of happiness, crowd-sourcing innovation, power shifts, or inexplicable connections. The TED Radio Hour is hosted by Guy Raz, and is a co-production of NPR & TED. Follow the show @TEDRadioHour.","thumbnail_url":"https://media.npr.org/assets/img/2015/03/18/ted_sq-3426270a541795b78233a698dd7965d407545cf3.jpg?s=1400","author":"NPR","episodes_sort_order":3},{"id":3127,"uuid":"0cc43410-1d2f-012e-0175-00163e1b201c","url":"http://99percentinvisible.org","title":"99% Invisible","description":"Design is everywhere in our lives, perhaps most importantly in the places where we've just stopped noticing. 99% Invisible is a weekly exploration of the process and power of design and architecture. From award winning producer Roman Mars. Learn more at 99percentinvisible.org.\nA proud member of Radiotopia, from PRX. Learn more at radiotopia.fm.","thumbnail_url":"http://cdn-99percentinvisible.prx.org/wp-content/uploads/powerpress/99-1400.png","author":"Roman Mars","episodes_sort_order":3},{"id":84789,"uuid":"1ed15140-f0c4-012e-e28c-525400c11844","url":"http://yehoodi.libsyn.com","title":"Hey Mister Jesse","description":"Welcome to Hey Mister Jesse's barbecue of tasty talk about swingin' jazz and blues. DJ Jesse Miner will serve up a platter of swingin' music that matters to dancers. Check out the show every month to hear music, interviews, listener feedback and news about the music swing dancers love.","thumbnail_url":"http://static.libsyn.com/p/assets/b/9/9/8/b998079820e26cfc/HMJ_Logo_color1440square.png","author":"Yehoodi","episodes_sort_order":3},{"id":287998,"uuid":"25a35340-5e97-0131-8f2a-723c91aeae46","url":"https://www.functionalgeekery.com","title":"Functional Geekery","description":"Functional Geeks, Geeking Functionally","thumbnail_url":"https://www.functionalgeekery.com/wp-content/cache/podlove/02/531ee088f7bf3317a94b498831307c/functional-geekery_original.jpg","author":"Proctor","episodes_sort_order":3},{"id":1663,"uuid":"37187090-0be2-012e-fb10-00163e1b201c","url":"https://twit.tv/shows/security-now","title":"Security Now (MP3)","description":"Steve Gibson, the man who coined the term spyware and created the first anti-spyware program, creator of Spinrite and ShieldsUP, discusses the hot topics in security today with Leo Laporte.\n\nRecords live every Tuesday at 4:30pm Eastern / 1:30pm Pacific / 21:30 UTC.","thumbnail_url":"http://elroy.twit.tv/sites/default/files/styles/twit_album_art_2048x2048/public/images/shows/security_now/album_art/audio/sn1400audio.jpg?itok=WZIaoXS4","author":"TWiT","episodes_sort_order":3},{"id":622537,"uuid":"372f17e0-50bb-0133-c349-0d11918ab357","url":"http://podkastas.lt","title":"Podkastass","description":"Požiurį į gyvenimą keičiantys pokalbiai","thumbnail_url":"http://podkastas.lt/itunes/podkastas-cover-2800x2800.jpg","author":"Justas Markus, Tomas Laurinavičius","episodes_sort_order":3},{"id":1954,"uuid":"3ec78c50-0d62-012e-fb9c-00163e1b201c","url":"http://www.dancarlin.com","title":"Dan Carlin's Hardcore History","description":"In \"Hardcore History\" journalist and broadcaster Dan Carlin takes his \"Martian\", unorthodox way of thinking and applies it to the past. Was Alexander the Great as bad a person as Adolf Hitler? What would Apaches with modern weapons be like? Will our modern civilization ever fall like civilizations from past eras? This isn't academic history (and Carlin isn't a historian) but the podcast's unique blend of high drama, masterful narration and Twilight Zone-style twists has entertained millions of listeners.","thumbnail_url":"http://www.dancarlin.com/graphics/DC_HH_iTunes.jpg","author":"Dan Carlin","episodes_sort_order":3},{"id":1340,"uuid":"460b29b0-0426-012e-f9a0-00163e1b201c","url":"http://thisdeveloperslife.com","title":"This Developer's Life","description":"Bringing a human slant to the tech industry","thumbnail_url":"http://media.wekeroad.com/tdl2.png","author":"Rob Conery and Scott Hanselman","episodes_sort_order":3},{"id":614468,"uuid":"53a17840-4606-0133-c14a-0d11918ab357","url":"http://newrustacean.com","title":"New Rustacean","description":"Documenting a journey into a new programming language—with source code, examples, and almost certainly some out-and-out hilarities along the way.","thumbnail_url":"http://newrustacean.com/podcast.png","author":"Chris Krycho","episodes_sort_order":3},{"id":207672,"uuid":"70d13d50-9efe-0130-1b90-723c91aeae46","url":"http://changelog.fm","title":"The Changelog","description":"A weekly podcast that covers the technology and people of open source. It's about the code, the people, and the community.","thumbnail_url":"https://cdn.changelog.com/images/podcasts/podcast-cover-art-64a3184278271e1751c20f040e3c0055.png?vsn=d","author":"Changelog Media","episodes_sort_order":3},{"id":664552,"uuid":"855b3b70-b3da-0133-2e57-6dc413d6d41d","url":"https://michaelandevita.com","title":"The Michael & Evita Show","description":"Michael and Evita, world class Lindy Hop dancers, bring you a new show dedicated to Swing dancing and all it entails. In each episode they answer questions from students around the world and tell stories from a lifetime in dance.","thumbnail_url":"https://mandevita.wpengine.com/wp-content/uploads/powerpress/The_Michael_and_Evita_Show.jpg","author":"Michael & Evita : World Class Lindy Hop","episodes_sort_order":3},{"id":268209,"uuid":"8d728390-249c-0131-73be-723c91aeae46","url":"http://www.samharris.org","title":"Waking Up with Sam Harris","description":"Join neuroscientist, philosopher, and best-selling author Sam Harris as he explores important and controversial questions about the human mind, society, and current events. \n\nSam Harris is the author of The End of Faith, Letter to a Christian Nation, The Moral Landscape, Free Will, Lying, Waking Up, and Islam and the Future of Tolerance (with Maajid Nawaz). The End of Faith won the 2005 PEN Award for Nonfiction. His writing has been published in more than 20 languages. Mr. Harris and his work have been discussed in The New York Times, Time, Scientific American, Nature, Newsweek, Rolling Stone, and many other journals. His writing has appeared in The New York Times, The Los Angeles Times, The Economist, Newsweek, The Times (London), The Boston Globe, The Atlantic, The Annals of Neurology, and elsewhere.\n\nMr. Harris received a degree in philosophy from Stanford University and a Ph.D. in neuroscience from UCLA.","thumbnail_url":"http://i1.sndcdn.com/avatars-000133036173-moxmo8-original.jpg","author":"Waking Up with Sam Harris","episodes_sort_order":3},{"id":294669,"uuid":"8fa0f3b0-6fdd-0131-7b79-723c91aeae46","url":"http://www.hellointernet.fm/","title":"Hello Internet","description":"CGP Grey and Brady Haran talk about YouTube, life, work, whatever.","thumbnail_url":"http://static1.squarespace.com/static/52d66949e4b0a8cec3bcdd46/t/52ebf67fe4b0f4af2a4502d8/1391195777839/1500w/Hello+Internet.003.png","author":"CGP Grey & Brady Haran","episodes_sort_order":3},{"id":579973,"uuid":"a0b34860-0b54-0133-2082-059c869cc4eb","url":"http://www.cmpod.net","title":"Curious Minds Podcast","description":"A podcast about Science, Technology & History. Funny, Smart and In-Depth. Hosted By Ran Levi (B.Sc EE) and Kelly O'Laughlin (Author, \"A Highly Sensitive Person's Life\"). For more info visit www.cmpod.net","thumbnail_url":"http://www.cmpod.net/wp-content/uploads/powerpress/CM_Logo_With_Text_3000x3000.jpg","author":"Ran Levi","episodes_sort_order":3},{"id":364166,"uuid":"a0d69a80-04e7-0132-a2c3-5f4c86fd3263","url":"http://typetheorypodcast.com","title":"The Type Theory Podcast","description":"We interview experts and researchers in type theory, from the perspectives of programming, mathematics, and philosophy.","thumbnail_url":"http://typetheorypodcast.com/wp-content/cache/podlove/77/da02dbb19d4f65cd39b7b02362f7a2/the-type-theory-podcast_original.png","author":"The Type Theory Podcast","episodes_sort_order":3},{"id":173168,"uuid":"c96d7b90-55d5-0130-e92d-723c91aeae46","url":"http://thescalawags.libsyn.com","title":"thescalawags's podcast","description":"A discussion-oriented podcast relating to the Scala programming language and its community","thumbnail_url":"http://static.libsyn.com/p/assets/d/f/0/7/df079a24e19ab32c/scalawags.png","author":"Scalawags Podcast","episodes_sort_order":3},{"id":702473,"uuid":"dccee090-0f40-0134-9d00-59d98c6b72b8","url":"http://withinthewires.libsyn.com/podcast","title":"Within the Wires","description":"Introducing a new podcast from the team behind Welcome to Night Vale and Alice Isn't Dead. Set within an alternate reality, Within the Wires tells stories in the guise of instructional audio programs. Each season guides the listener through a different audio experience from this other universe, ushering listeners through a curriculum which unravels to reveal a much deeper and more personal story. Season one, \"Relaxation Cassettes\", premieres June 21, 2016. New episodes every other Tuesday through October 25. Created by Jeffrey Cranor. Written by Jeffrey Cranor and Janina Matthewson. Original music by Mary Epworth. Performed by Janina Matthewson. Part of the Night Vale Presents network.","thumbnail_url":"http://static.libsyn.com/p/assets/8/f/c/3/8fc3b500ea7599f6/WithinTheWires.jpg","author":"Night Vale Presents","episodes_sort_order":3},{"id":1375,"uuid":"ec18bbd0-0426-012e-f9a0-00163e1b201c","url":"http://www.hanselminutes.com","title":"Hanselminutes","description":"Hanselminutes - Fresh Air for Developers is a weekly talk show that brings interesting people together to talk about the web, culture, education, technology and more. Hosted by Scott Hanselman, this show promises fresh ideas and great people.","thumbnail_url":"http://www.pwop.com/itunes_hanselminutes.jpg","author":"Scott Hanselman","episodes_sort_order":3},{"id":1107,"uuid":"f5b97290-0422-012e-f9a0-00163e1b201c","url":"http://www.radiolab.org/series/podcasts/","title":"Radiolab","description":"Radiolab is a show about curiosity. Where sound illuminates ideas, and the boundaries blur between science, philosophy, and human experience.\nRadiolab is heard around the country on more than 500 member stations. Check your local station for airtimes.\nEmbed the Radiolab widget on your blog or website.\n\nRadiolab is supported, in part, by the Alfred P. Sloan Foundation, enhancing public understanding of science and technology in the modern world. More information about Sloan at www.sloan.org.\nAll press inquiries may be directed to Jennifer Houlihan Roussel at (646) 829-4497.","thumbnail_url":"https://media2.wnyc.org/i/1400/1400/l/80/1/Radiolab-wnycstudios.jpg","author":"WNYC Studios","episodes_sort_order":3}]"""

  class Backend($: BackendScope[Unit, State]) {

    val renderPodcast = (podcast: Podcast, idx: Int) =>
      <.div(
        ^.cls := s"mdl-card mdl-cell mdl-cell--$idx-col",
        <.div(
          ^.cls := "mdl-card__media mdl-color-text--grey-50",
          ^.backgroundImage := s"url('${podcast.thumbnailUrl}')",
          ^.backgroundSize := "cover"
        ),
        <.div(
          ^.cls := "mdl-card__supporting-text meta mdl-color-text--grey-600",
          <.div(
            <.strong(
              <.a(
                ^.href := podcast.url,
                ^.target := "_blank",
                podcast.title
              )
            ),
            <.span(podcast.author)
          )
        )
      )

    def render(state: State) = {
      val layout = Layout.generateLayout(state.podcasts.size, new Random(state.email.hashCode))
      val renderedPodcasts = state.podcasts.zip(layout.flatMap(_.idx)).map(renderPodcast.tupled)

      <.div(
        ^.cls := "demo-blog__posts mdl-grid",
        renderedPodcasts
      )
    }
  }

  val component =
    ReactComponentB[Unit]("Subscriptions")
      .initialState(State("mmartynas@gmail.com", read[Seq[Podcast]](allJson)))
      .renderBackend[Backend]
      .build

  def apply(email: String) = component()

}
