<script>
	import NavigationBar from "$lib/components/NavigationBar.svelte";
	import ContentPage from "$lib/templates/ContentPage.svelte";
</script>

<ContentPage
	title="One Billion Row Challenge">
	<div class="left">
		<ul>
			<li>Read a <b>text file</b> containing <b>1 billion rows</b> of measurements.</li>
			<li>Each line contains a weather <b>station name</b> and <b>temperature</b>:
				<ul>
					<li>Rows are delimited by a newline character (<b>'\n'</b>).</li>
					<li>Station name and temperature are separated by a semicolon.</li>
					<li>Station name is encoded in <b>UTF-8</b> and is <b>100 bytes</b> or shorter.</li>
					<li>The temperature ranges from -99.9 to 99.9 (<b>1-2 digits before</b> the decimal point and <b>1 digit after</b>).</li>
				</ul>
			</li>
			<li>Calculate the <b>minimum</b>, <b>maximum</b>, and <b>average</b> temperatures for <b>each station</b>.</li>
			<li>Print the results in the format of Java <b>sorted-map `toString()`</b>.</li>
			<li><b>No external dependencies</b>.</li>
			<li>All source code should be in a <b>single file</b>.</li>
			<li>Benchmark on a <b>32-core CPU</b> with <b>128 GB RAM</b>.</li>
		</ul>		
	</div>
	<div class="right">
		<div class="tab">
			<svg xmlns="http://www.w3.org/2000/svg" width="16" height="16" fill="none" stroke="currentColor" stroke-width="2" stroke-linecap="round" stroke-linejoin="round">
				<line x1="3" y1="4" x2="16" y2="4"/>
				<line x1="3" y1="8" x2="11" y2="8"/>
				<line x1="3" y1="12" x2="16" y2="12"/>
				<line x1="3" y1="16" x2="11" y2="16"/>
			</svg>
			measurements.txt
		</div>
		<table class="editor">
			<tr>
			<td class="linenums">
				<div>1</div>
				<div>2</div>
				<div>3</div>
				<div>4</div>
				<div class="blur">455</div>
				<div class="blur">15,466</div>
				<div class="blur">1,546,536</div>
				<div>1,000,000,000</div>
			</td>
			<td class="code">
				<div>Ottawa;14.2</div>
				<div>Bangkok;44.0</div>
				<div>Sydney;-2.5</div>
				<div>Łódź;-20.4</div>
				<div class="blur">Mercury;-50.7</div>
				<div class="blur">Mars;-4.7</div>
				<div class="blur">Venus;84.5</div>
				<div>Budapest;14.9</div>
			</td>
			</tr>
		</table>
		<p>
			File size: <b>13GB</b>
		</p>
	</div>
</ContentPage>
<NavigationBar
	prevLink="./results"
	nextLink="./solution"
/>

<style>
	b {
		color: burlywood;
	}
	ul {
		margin: 0px;
	}
	ul li ul {
		font-size: 0.9em;
	}
	.left,
	.right {
		margin: 0px;
		padding: 0px;
		display: inline-block;
		vertical-align: top;
		text-align: left;
	}
	.left {
		width: 60%;
	}
	.right {
		padding: 1em;
		width: 30%;
	}
	.right table {
		border-collapse: collapse;
		border: none;
	}
	.tab {
		padding-left: 0.5em;
	}
	.linenums {
		width: 60%;
		text-align: right;
		color: lightslategray;
	}
	.code {
		width: 40%;
	}
	.code div::after {
		content: "¶";
		color: #777;
	}
	.code div.blur::after,
	.blur {
		color: transparent;
		text-shadow: 0 0 8px rgba(240, 248, 255, 0.8) !important;
	}
	.tab, .linenums, .code {
		background-color: #1f1f1f;
		border: 1px solid lightslategray !important;
		font-family: 'Fira Code', monospace;
	}
	.linenums, .code {
		line-height: 1.2;
	}
	.linenums div, .code div {
		line-height: 1.2;
		margin: 0;
		padding-left: 0.5em;
		padding-right: 0.5em;
		padding-top: 0.0em;
		padding-bottom: 0.0em;
	}
</style>
