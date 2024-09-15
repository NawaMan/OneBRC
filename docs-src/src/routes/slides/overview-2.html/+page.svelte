<script>
	import NavigationBar from "$lib/components/NavigationBar.svelte";
	import ContentPage from "$lib/templates/ContentPage.svelte";
	import YouTube from "$lib/components/YouTube.svelte";
</script>

<ContentPage title="Overview">
	<table class="main-table">
		<tr>
			<td class="text">
				<p>
					The One Billion Row Challenge (1BRC) 
						<a href="https://x.com/gunnarmorling/status/1741839724933751238"  data-tooltip=" Annoucement on X " data-placement="top" target="_blank">🔗</a>
						<a href="https://www.morling.dev/blog/one-billion-row-challenge/" data-tooltip=" 1BRC detail page " data-placement="top" target="_blank">🔗</a>, 
					proposed by Gunnar Morling, 
					is a coding challenge designed to test the limits of <b>Java</b>'s performance capabilities. 
					Launched on <b>January 1, 2024</b>, 
					the challenge invites participants to create the fastest Java program for processing 
					a text file containing <b>one billion rows</b> of temperature measurements.
				</p>
				<div class="text-container">
					<div class="text-left"><span class="bound">CPU BOUND</span></div>
					<div class="text-right">
						<img class="drake" src="../drake-yes.png" alt="Drake say 'YES'." />
						August 2024.
					</div>
				</div>
			</td>
			<td width="370px">
				<YouTube name="../1brc-in-2seconds" youtubeId="9-S_nZ5gzGE" width="370px" />
				<YouTube name="../1brc"             youtubeId="RYjB4sGXNZI" width="370px" />
			</td>
		</tr>
	</table>
	<img class="glasses" src="../ThugLifeGlasses.png" alt="Glasses" />
</ContentPage>
<NavigationBar
	prevLink="./overview.html"
	nextLink="./results.html"
/>

<style>
	a {
		text-decoration: none;
	}
	b {
		color: burlywood;
	}
	.main-table {
		height: 560px;
	}
	.text {
		padding: 2em;
		width: 720px;
	}
	.text-container {
		display: flex;
		justify-content: space-between;
		align-items: center;
	}
	.text-left {
		width: 70%;
		text-align: center;
	}
	.text-right {
		width: 30%;
		text-align: right;
	}
	.bound {
		color: aquamarine;
		font-weight: bold;
		font-size: 2.5em;
		font-family: Cambria, Cochin, Georgia, Times, 'Times New Roman', serif;
	}
	.drake {
		width: 200px;
		float: right;
		border-radius: 3px;
		text-align: center;
	}
	.glasses {
		position: absolute;
		width: 90px;
		height: 25px;
		top: 487px;
		left: 1070px;
		overflow: visible;
	}
</style>
