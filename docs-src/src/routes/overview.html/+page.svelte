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
	.text {
		padding: 2em;
	}
</style>

<ContentPage title="Overview">
	<table>
		<tr>
			<td class="text">
				The One Billion Row Challenge (1BRC) 
					<a href="https://x.com/gunnarmorling/status/1741839724933751238" data-tooltip="Annoucement on X" data-placement="top" target="_blank">🔗</a>
					<a href="https://www.morling.dev/blog/one-billion-row-challenge/" data-tooltip="1BRC detail page" data-placement="top" target="_blank">🔗</a>, 
				proposed by Gunnar Morling, 
				is a coding challenge designed to test the limits of <b>Java</b>'s performance capabilities. 
				Launched on <b>January 1, 2024</b>, 
				the challenge invites participants to create the fastest Java program for processing 
				a text file containing <b>one billion rows</b> of temperature measurements.
			</td>
			<td>
				<a href="https://x.com/gunnarmorling/status/1741839724933751238" data-tooltip="Annoucement on X" data-placement="top" target="_blank">
					<img src="1brc-on-X.png" alt="1BRC" height="550px" />
				</a>
			</td>
		</tr>
	</table>
</ContentPage>
<NavigationBar
	prevLink="/"
	nextLink="/overview-2.html"
/>