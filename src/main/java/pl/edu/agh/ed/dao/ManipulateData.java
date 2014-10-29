package pl.edu.agh.ed.dao;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.Reader;
import java.net.URL;
import java.nio.charset.Charset;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Comparator;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.TreeMap;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.apache.commons.lang3.StringUtils;
import org.hibernate.SessionFactory;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.nodes.Node;
import org.jsoup.select.Elements;

import pl.edu.agh.ed.objects.Author;
import pl.edu.agh.ed.objects.Category;
import pl.edu.agh.ed.objects.Comment;
import pl.edu.agh.ed.objects.CommentTopic;
import pl.edu.agh.ed.objects.Post;
import pl.edu.agh.ed.objects.PostTag;
import pl.edu.agh.ed.objects.PostTopic;
import pl.edu.agh.ed.objects.Tag;
import pl.edu.agh.ed.objects.Topic;

public class ManipulateData {
	static SessionFactory factory;

	private static final String[] avoids = new String[] { "i", "a", "about",
			"an", "and", "are", "as", "at", "be", "by", "com", "de", "en",
			"for", "from", "how", "in", "is", "it", "la", "of", "on", "or",
			"that", "the", "this", "to", "was", "what", "when", "where", "who",
			"will", "with", "und", "the", "www", "their", "have", "had",
			"would", "us" };
	private static final String PATTERN_DELETE_TAGS = "(?i)<(?!(/?(a)))[^>]*>";
	private static final String PATTERN_GET_SITE_ID = "\\_n_([^)]+)\\.html";
	private static final int MAX_NUM_RES = 50;
	private static final String TITLE_CLASS = "title";
	private static final String CONTENT_ID = "mainentrycontent";
	private static final String QUERY = "http://www.huffingtonpost.com/2014/10/";
	private static final String FB_COMMENTS_START = "https://www.facebook.com/plugins/comments.php?"
			+ "api_key=46744042133"
			+ "&channel_url=http%3A%2F%2Fstatic.ak.facebook.com%2Fconnect%2Fxd_arbiter%2F2_ZudbRXWRs.js%3Fversion%3D41%23cb%3Df1ce5c815c%26domain%3Dwww.huffingtonpost.com%26origin%3Dhttp%253A%252F%252Fwww.huffingtonpost.com%252Ffad39a6b%26relation%3Dparent.parent"
			+ "&colorscheme=light" + "&href=";
	private static final String FB_COMMENTS_END = "&locale=en_US"
			+ "&numposts=50" + "&sdk=joey" + "&skin=light" + "&width=570";
	private static final String EMBED_START = "http://api.embed.ly/1/extract?key=49584e957b1b4f3b988a2a2ea36cc704&url=";
	private static final String EMBED_END = "&format=json";

	public static void main(String[] args) {

		// AuthorManipulate AM = new
		// AuthorManipulate(FactoryMaker.getSessionFactory(Author.class));
		//
		// Author a = AM.getAuthorStartWith("Brian");
		// System.out.println(a);
		// CategoryManipulate CM = new
		// CategoryManipulate(FactoryMaker.getSessionFactory(Category.class));
		//
		//
		// Category category = CM.addCategory("kategoria3333");
		// PostManipulate PM = new
		// PostManipulate(FactoryMaker.getSessionFactory(Post.class));
		//
		// PM.addPost("treњж", new Date(), "www.wp.pl", "tytul", a, category,
		// Boolean.TRUE);

		// AuthorManipulate AM = new AuthorManipulate(
		// FactoryMaker.getSessionFactory(Author.class));
		// Author a = AM.getAuthorByName("Brian");

		parsePageAndAddToDB("http://www.huffingtonpost.com/2014/08/25/beyonce-feminist-vmas_n_5708475.html");
	}

	private static void parsePageAndAddToDB(String site) {
		try {
			PostManipulate PM = new PostManipulate(
					FactoryMaker.getSessionFactory(Post.class));
			CommentTopicManipulate CTopicM = new CommentTopicManipulate(
					FactoryMaker.getSessionFactory(CommentTopic.class));
			AuthorManipulate AM = new AuthorManipulate(
					FactoryMaker.getSessionFactory(Author.class));
			CategoryManipulate CM = new CategoryManipulate(
					FactoryMaker.getSessionFactory(Category.class));
			TagManipulate TM = new TagManipulate(
					FactoryMaker.getSessionFactory(Tag.class));
			PostTagManipulate PTM = new PostTagManipulate(
					FactoryMaker.getSessionFactory(PostTag.class));
			TopicManipulate TopicM = new TopicManipulate(
					FactoryMaker.getSessionFactory(Topic.class));
			CommentManipulate CommentM = new CommentManipulate(
					FactoryMaker.getSessionFactory(Comment.class));
			PostTopicManipulate PTopicM = new PostTopicManipulate(
					FactoryMaker.getSessionFactory(PostTopic.class));
			if (PM.checkIfExistsLink(site)) {
				System.err.println("Post juz istnieje!!!");
				return;
			}
			Document doc = Jsoup.connect(site).get();
			// 1
			Author author = new Author();
			getAuthor(author, doc);
			Author findedAuthor = AM.getAuthorByName(author.getName());
			if (findedAuthor != null) {
				author = findedAuthor;
			} else {
				if (author.getLink() == null && author.getName() == null) {
					System.err.println("Autor jest pusty!!!");
					return;
				}
				AM.addAuthor(author);
				if (author.getId() == 0) {
					System.err.println("Autor nie zapisal sie!!!");
					return;
				}
			}
			// 2
			Category category = new Category();
			getCategory(category, doc);

			Category findedCategory = CM.getCategoryByName(category
					.getCategoryName());
			if (findedCategory != null) {
				category = findedCategory;
			} else {
				if (category.getCategoryName() == null) {
					System.err.println("Kategoria jest pusta!!!");
					return;
				}
				CM.addCategory(category);
				if (category.getId() == 0) {
					System.err.println("Kategoria nie zapisala sie!!!");
					return;
				}
			}
			// 3
			Post post = new Post();
			post.setLink(site.substring(0,
					site.indexOf(".html") + ".html".length()));
			getSiteNum(site, post);
			getDate(post, doc);
			getTitle(post, doc);
			getContent(post, doc);
			post.setZrobiona(false);
			post.setAuthor(author);
			post.setCategory(category);
			// Post findedPost = PM.getPostByLink(post.getLink());
			// if (findedPost != null) {
			// post = findedPost;
			// } else {
			PM.addPost(post);
			if (post.getId() == 0) {
				System.err.println("Post sie nie zapisal!!!");
				return;
			}
			// }

			// 4 + 5
			for (Element el : doc.getElementsByAttributeValue("name",
					"sailthru.tags")) {
				for (String tagString : Arrays.asList(el.attr("content").split(
						"\\s*,\\s*"))) {
					Tag tag = new Tag();
					tag.setName(tagString);
					Tag findedTag = TM.getTagByName(tag);
					if (findedTag != null) {
						tag = findedTag;
					} else {
						TM.addTag(tag);
						if (tag.getId() == 0) {
							System.err.println("Tag nie zapisal sie!!!");
							return;
						}
					}
					PostTag postTag = new PostTag();
					postTag.setPost_id(post);
					postTag.setTag_id(tag);
					PTM.addPostTag(postTag);
				}

			}
			// 6 + 7
			Topic topic = new Topic();
			getTopic(topic, doc, site);
			Topic findedTopic = TopicM.getTopicByName(topic.getKeywords());
			if (findedTopic != null) {
				topic = findedTopic;
			} else {
				if (topic.getKeywords() == null) {
					System.err.println("Topic jest pusty!!!");
					// return;
				}
				TopicM.addTopic(topic);
				if (topic.getId() == 0) {
					System.err.println("Topic nie zapisal sie!!!");
					return;
				}
			}

			PostTopic postTopic = new PostTopic();
			postTopic.setPost_id(post);
			postTopic.setTopic_id(topic);
			PTopicM.addPostTopic(postTopic);
			// 8+9
			Document docFb = Jsoup.connect(
					FB_COMMENTS_START + site + FB_COMMENTS_END).get();

			for (Element el : docFb.getElementsByClass("fbTopLevelComment")) {
				Comment comment = new Comment();
				comment.setPost(post);
				Element contentElement = null;
				boolean shouldSearch = true;
				Elements elements = el.getElementsByTag("div");
				for (int i = 0; i < elements.size() && shouldSearch; i++) {
					Element elDiv = elements.get(i);
					System.out.println("elDiv:" + elDiv.className());
					if (elDiv.className().startsWith("postContainer")) {
						contentElement = elDiv;
						shouldSearch = false;
					}
				}
				for (Element elAuthor : contentElement
						.getElementsByClass("profileName")) {
					System.out.println("elAuthor:" + elAuthor.className());
					author = new Author();
					author.setName(elAuthor.html());
					author.setLink(elAuthor.attr("href").equals("#") ? author
							.getName() : elAuthor.attr("href"));
					findedAuthor = AM.getAuthorByName(author.getName());
					if (findedAuthor != null) {
						author = findedAuthor;
					} else {
						if (author.getLink() == null
								&& author.getName() == null) {
							System.err.println("Autor jest pusty!!!");
							return;
						}
						AM.addAuthor(author);
						if (author.getId() == 0) {
							System.err.println("Autor nie zapisal sie!!!");
							return;
						}
					}
					comment.setAuthor(author);
				}
				for (Element elText : contentElement
						.getElementsByClass("postText")) {
					System.out.println("elText:" + elText.className());
					comment.setContent(elText.html().replaceAll(
							PATTERN_DELETE_TAGS, ""));
				}
				for (Element elDate : contentElement.getElementsByTag("abbr")) {
					System.out.println("elDate:" + elDate.className());
					DateFormat dateFormat = new SimpleDateFormat(
							"EEEE, MMMM d, yyyy 'at' h:mma", Locale.US);
					comment.setDate(dateFormat.parse(elDate.attr("title")));
				}

				Comment findedComment = CommentM.getCommentByName(comment);
				if (findedComment != null) {
					comment = findedComment;
				} else {
					if (comment.getContent() == null
							&& comment.getTitle() == null) {
						System.err.println("Koment jest pusty!!!");
						return;
					}
					CommentM.addComment(comment);
					if (comment.getId() == 0) {
						System.err.println("Koment nie zapisal sie!!!");
						return;
					}
				}
				topic = new Topic();
				topic.setKeywords(countWords(comment.getContent()));
				findedTopic = TopicM.getTopicByName(topic.getKeywords());
				if (findedTopic != null) {
					topic = findedTopic;
				} else {
					if (topic.getKeywords() == null) {
						System.err.println("Topic jest pusty!!!");
						return;
					}
					TopicM.addTopic(topic);
					if (topic.getId() == 0) {
						System.err.println("Topic nie zapisal sie!!!");
						return;
					}
				}
				CommentTopic commentTopic = new CommentTopic();
				commentTopic.setComment_id(comment);
				commentTopic.setTopic_id(topic);
				CTopicM.addCommentTopic(commentTopic);

				for (Element elReplies : el.child(0).getElementsByClass(
						"fbCommentReply")) {
					System.out.println("elReplies:" + elReplies.className());
					Comment commentReply = new Comment();
					commentReply.setPost(post);
					Element contentElementReply = null;
					boolean shouldSearchReply = true;
					Elements elementsReply = elReplies.getElementsByTag("div");
					for (int i = 0; i < elementsReply.size()
							&& shouldSearchReply; i++) {
						Element elDiv = elementsReply.get(i);
						System.out.println("elDiv:" + elDiv.className());
						if (elDiv.className().startsWith("postContainer")) {
							contentElementReply = elDiv;
							shouldSearchReply = false;
						}
					}
					for (Element elAuthor : contentElementReply
							.getElementsByClass("profileName")) {
						System.out.println("elAuthor:" + elAuthor.className());
						author = new Author();
						author.setName(elAuthor.html());
						author.setLink(elAuthor.attr("href").equals("#") ? author
								.getName() : elAuthor.attr("href"));
						findedAuthor = AM.getAuthorByName(author.getName());
						if (findedAuthor != null) {
							author = findedAuthor;
						} else {
							if (author.getLink() == null
									&& author.getName() == null) {
								System.err.println("Autor jest pusty!!!");
								return;
							}
							AM.addAuthor(author);
							if (author.getId() == 0) {
								System.err.println("Autor nie zapisal sie!!!");
								return;
							}
						}
						commentReply.setAuthor(author);
					}
					for (Element elText : contentElementReply
							.getElementsByClass("postText")) {
						System.out.println("elText:" + elText.className());
						commentReply.setContent(elText.html().replaceAll(
								PATTERN_DELETE_TAGS, ""));
					}
					for (Element elDate : contentElementReply
							.getElementsByTag("abbr")) {
						System.out.println("elDate:" + elDate.className());
						DateFormat dateFormat = new SimpleDateFormat(
								"EEEE, MMMM d, yyyy 'at' h:mma", Locale.US);
						commentReply.setDate(dateFormat.parse(elDate
								.attr("title")));
					}
					commentReply.setComment(comment);

					findedComment = CommentM.getCommentByName(commentReply);
					if (findedComment != null) {
						commentReply = findedComment;
					} else {
						if (commentReply.getContent() == null
								&& commentReply.getTitle() == null) {
							System.err.println("Koment jest pusty!!!");
							return;
						}
						CommentM.addComment(commentReply);
						if (commentReply.getId() == 0) {
							System.err.println("Koment nie zapisal sie!!!");
							return;
						}
					}
					topic = new Topic();
					topic.setKeywords(countWords(commentReply.getContent()));
					findedTopic = TopicM.getTopicByName(topic.getKeywords());
					if (findedTopic != null) {
						topic = findedTopic;
					} else {
						if (topic.getKeywords() == null) {
							System.err.println("Topic jest pusty!!!");
							return;
						}
						TopicM.addTopic(topic);
						if (topic.getId() == 0) {
							System.err.println("Topic nie zapisal sie!!!");
							return;
						}
					}

					commentTopic = new CommentTopic();
					commentTopic.setComment_id(commentReply);
					commentTopic.setTopic_id(topic);
					CTopicM.addCommentTopic(commentTopic);
				}

			}
			System.out.println("KONIEC!!! WYLACZ APKE!!!");
		} catch (IOException e) {
			e.printStackTrace();
		} catch (ParseException e) {
			e.printStackTrace();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	private static String countWords(String text) {
		text = text.toLowerCase();
		// remove any "\n" characters that may occur
		String temp = text.replaceAll("[\\n]", " ");

		// replace any grammatical characters and split the String into an array
		String[] splitter = temp.replaceAll("[.,?!:;/]", "").split(" ");

		// intialize an int array to hold count of each word

		List<String> splitterList = new ArrayList<String>(
				Arrays.asList(splitter));

		int[] counter = new int[splitterList.size()];

		// loop through the sentence
		for (int i = 0; i < splitterList.size(); i++) {

			// hold current word in the sentence in temp variable
			temp = splitterList.get(i);

			// inner loop to compare current word with those in the sentence
			// incrementing the counter of the adjacent int array for each match
			for (int k = 0; k < splitterList.size(); k++) {

				if (temp.equalsIgnoreCase(splitterList.get(k))) {
					counter[k]++;
				}
			}
		}
		Map<String, Integer> words = new LinkedHashMap<String, Integer>();
		for (int i = 0; i < splitterList.size(); i++) {
			words.put(splitterList.get(i), counter[i]);
		}

		for (String avoid : avoids) {
			words.remove(avoid);
		}

		return connectStrings(words);
	}

	private static String connectStrings(Map<String, Integer> words) {
		ValueComparator bvc = new ValueComparator(words);
		TreeMap<String, Integer> sorted_map = new TreeMap<String, Integer>(bvc);
		sorted_map.putAll(words);
		String s = "";
		if (words.size() <= 10) {
			s = StringUtils.join(sorted_map.keySet(), " ");
		} else {
			int size = words.size();
			int i = 0;
			Iterator<String> it = sorted_map.keySet().iterator();
			while (i < 10 && it.hasNext()) {
				s += (it.next() + " ");
				i++;
			}
		}
		return s;
	}

	private static class ValueComparator implements Comparator<String> {

		Map<String, Integer> base;

		public ValueComparator(Map<String, Integer> base) {
			this.base = base;
		}

		// Note: this comparator imposes orderings that are inconsistent with
		// equals.
		public int compare(String a, String b) {
			if (base.get(a) >= base.get(b)) {
				return -1;
			} else {
				return 1;
			} // returning 0 would merge keys
		}
	}

	private static void getSiteNum(String site, Post post) {
		Matcher m = Pattern.compile(PATTERN_GET_SITE_ID).matcher(site);
		while (m.find()) {
			post.setSite(Integer.valueOf(m.group(1)));
			System.out.println(m.group(1));
		}
	}

	private static void getTopic(Topic topic, Document doc, String site)
			throws IOException {
		JSONObject json = readJsonFromUrl(EMBED_START + site + EMBED_END);
		JSONArray keywordsObjects = json.getJSONArray("keywords");
		List<String> topics = new ArrayList<String>();
		for (int i = 0; i < keywordsObjects.length(); i++) {
			JSONObject keywordObject = keywordsObjects.getJSONObject(i);
			topics.add(keywordObject.getString("name"));
			System.out.println(keywordObject.toString());
		}
		topic.setKeywords(StringUtils.join(topics, " "));
	}

	private static String readAll(Reader rd) throws IOException {
		StringBuilder sb = new StringBuilder();
		int cp;
		while ((cp = rd.read()) != -1) {
			sb.append((char) cp);
		}
		return sb.toString();
	}

	public static JSONObject readJsonFromUrl(String url) throws IOException,
			JSONException {
		InputStream is = new URL(url).openStream();
		try {
			BufferedReader rd = new BufferedReader(new InputStreamReader(is,
					Charset.forName("UTF-8")));
			String jsonText = readAll(rd);
			JSONObject json = new JSONObject(jsonText);
			return json;
		} finally {
			is.close();
		}
	}

	// TODO:!!!
	private static void getComments(String site, Post post) throws IOException,
			ParseException {
		Document docFb = Jsoup.connect(
				FB_COMMENTS_START + site + FB_COMMENTS_END).get();
		List<Comment> comments = new ArrayList<Comment>();
		for (Element el : docFb.getElementsByClass("fbTopLevelComment")) {
			System.out.println("el:" + el.className());
			comments.add(createComment(el));
		}
		// post.setComments(comments);
	}

	// TODO: !!!
	private static Comment createComment(Element el) throws ParseException {
		Comment comment = new Comment();
		Element contentElement = null;
		boolean shouldSearch = true;
		Elements elements = el.getElementsByTag("div");
		for (int i = 0; i < elements.size() && shouldSearch; i++) {
			Element elDiv = elements.get(i);
			System.out.println("elDiv:" + elDiv.className());
			if (elDiv.className().startsWith("postContainer")) {
				contentElement = elDiv;
				shouldSearch = false;
			}
		}
		for (Element elAuthor : contentElement
				.getElementsByClass("profileName")) {
			System.out.println("elAuthor:" + elAuthor.className());
			Author author = new Author();
			author.setName(elAuthor.html());
			author.setLink(elAuthor.attr("href"));
			comment.setAuthor(author);
		}
		for (Element elText : contentElement.getElementsByClass("postText")) {
			System.out.println("elText:" + elText.className());
			comment.setContent(elText.html()
					.replaceAll(PATTERN_DELETE_TAGS, ""));
		}
		for (Element elDate : contentElement.getElementsByTag("abbr")) {
			System.out.println("elDate:" + elDate.className());
			DateFormat dateFormat = new SimpleDateFormat(
					"EEEE, MMMM d, yyyy 'at' h:mma", Locale.US);
			comment.setDate(dateFormat.parse(elDate.attr("title")));
		}

		List<Comment> repliesComments = new ArrayList<Comment>();
		for (Element elReplies : el.child(0).getElementsByClass(
				"fbCommentReply")) {
			System.out.println("elReplies:" + elReplies.className());
			repliesComments.add(createComment(elReplies));
		}
		// comment.setReplies(repliesComments);
		return comment;
	}

	private static void getCategory(Category category, Document doc) {
		for (Element el : doc.getElementsByAttributeValue("name", "category")) {
			category.setCategoryName(el.attr("content"));
			System.out.println(el.attr("content"));
		}
	}

	private static void getAuthor(Author author, Document doc) {
		for (Element el : doc.getElementsByAttributeValue("rel", "author")) {
			author.setName(el.html());
			author.setLink("http://www.huffingtonpost.com" + el.attr("href"));
			System.out.println(el.html());
		}
		if (author.getName() == null) {
			for (Element el : doc.getElementsByAttributeValue("class",
					"thirdparty-logo")) {
				author.setName(el.html());
				author.setLink(author.getName());
				System.out.println(el.html());
			}
		}
	}

	private static void getDate(Post post, Document doc) throws ParseException {
		for (Element el : doc.getElementsByClass("posted")) {
			for (Element inEl : el.getElementsByTag("time")) {
				DateFormat df = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ssXXX");
				post.setDate(df.parse(inEl.attr("datetime")));
			}
		}
	}

	private static void getTitle(Post post, Document doc) {
		Elements els = doc.getElementsByClass(TITLE_CLASS);
		for (Element el : els) {
			if (TITLE_CLASS.equals(el.className())) {
				for (Node node : el.childNodes()) {
					post.setTitle(node.toString());
					System.out.println(node.toString());
				}
			}
		}
	}

	private static void getContent(Post post, Document doc) {
		Element el = doc.getElementById(CONTENT_ID);
		String allContent = "";
		for (Element element : el.getElementsByTag("p")) {
			allContent += element.html().replaceAll(PATTERN_DELETE_TAGS, "");
			System.out.println(element.html().replaceAll(PATTERN_DELETE_TAGS,
					""));
		}
		post.setContent(allContent);
	}
}
