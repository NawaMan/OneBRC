<script>
	import NavigationBar from "$lib/components/NavigationBar.svelte";
	import ContentPage from "$lib/templates/ContentPage.svelte";
</script>

<style>
	a {
		text-decoration: none;
	}
	img {
		display: block;
		margin-left: auto;
		margin-right: auto;
		margin-bottom: 1em;
	}
	b {
		color: burlywood;
	}
</style>

<ContentPage title="Overview">
	<a href="https://www.perplexity.ai/search/how-would-you-introduce-the-1b-OtPqeqE_Q4K_ftdNWThnjQ">
		<img src="1brc.png" alt="1brc" width="500px" />
	</a>
	<p>
		The One Billion Row Challenge (1BRC) <a href="https://www.morling.dev/blog/one-billion-row-challenge/">🔗</a>, 
		  proposed by Gunnar Morling, 
		  is a coding challenge designed to test the limits of <b>Java</b>'s performance capabilities. 
		Launched on <b>January 1, 2024</b>, 
		  the challenge invites participants to create the fastest Java program for processing 
		  a text file containing <b>one billion rows</b> of temperature measurements.
	</p>
</ContentPage>
<NavigationBar
	prevLink="/"
	nextLink="/the-challenge.html"
/>