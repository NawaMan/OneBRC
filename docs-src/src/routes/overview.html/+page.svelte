<script>
	import NavigationBar from "$lib/components/NavigationBar.svelte";
	import ContentPage from "$lib/templates/ContentPage.svelte";
	import YouTube from "$lib/components/YouTube.svelte";
</script>

<style>
	a {
		text-decoration: none;
	}
	b {
		color: burlywood;
	}
</style>

<ContentPage title="Overview">
	<table>
		<tr>
			<td><YouTube name="1brc"             youtubeId="RYjB4sGXNZI" /></td>
			<td><YouTube name="1brc-in-2seconds" youtubeId="9-S_nZ5gzGE" /></td>
		</tr>
	</table>
	<p>
		The One Billion Row Challenge (1BRC) 
			<a href="https://x.com/gunnarmorling/status/1741839724933751238" data-tooltip="Annoucement on X" data-placement="top" target="_blank">🔗</a>
			<a href="https://www.morling.dev/blog/one-billion-row-challenge/" data-tooltip="1BRC detail page" data-placement="top" target="_blank">🔗</a>, 
		  proposed by Gunnar Morling, 
		  is a coding challenge designed to test the limits of <b>Java</b>'s performance capabilities. 
		Launched on <b>January 1, 2024</b>, 
		  the challenge invites participants to create the fastest Java program for processing 
		  a text file containing <b>one billion rows</b> of temperature measurements.
	</p>
</ContentPage>
<NavigationBar
	prevLink="/"
	nextLink="/results.html"
/>