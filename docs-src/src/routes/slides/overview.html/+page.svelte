<script>
	import ContentPage from "$lib/templates/ContentPage.svelte";
	import Note        from "$lib/components/Note.svelte";
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
					<div class="text-left"><span class="bound">I/O BOUND</span></div>
					<div class="text-right" style="text-align: center;">
						<img class="drake" src="../drake-no.png" alt="Drake say 'NO'." />
						January 2024
					</div>
				</div>
			</td>
			<td>
				<a href="https://x.com/gunnarmorling/status/1741839724933751238" target="_blank">
					<img src="../1brc-on-X.png" alt="1BRC" height="550px" />
				</a>
			</td>
		</tr>
	</table>
</ContentPage>
<Note>
	<p>The "One Billion Row Challenge" was proposed by Gunnar Morling on Jan 1st of 2024.</p>
	<p>The challenge is to write a program that can read a CSV file with one billion rows of data.</p>
	<p>
		I saw the original twitter post on that day but I thought this was an I/O bound problem which I am not interested in.
		and did not pay attention to it.
	</p>
</Note>

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
	.drake {
		width: 200px;
		float: right;
		text-align: center;
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
		color: tomato;
		font-weight: bold;
		font-size: 2.5em;
		font-family: Cambria, Cochin, Georgia, Times, 'Times New Roman', serif;
	}
	img {
		border-radius: 3px;
	}
</style>
