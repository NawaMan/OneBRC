<script>
	import NavigationBar from "$lib/components/NavigationBar.svelte";
	import ContentPage from "$lib/templates/ContentPage.svelte";
</script>

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
	.right table,
	.right table td {
		border-collapse: collapse;
		border: none;
		padding-left: 0.5em;
		padding-right: 0.5em;
	}
	.right table td:first-child {
		text-align: right;
	}
	.right table td:last-child {
		padding-left: 1em;
		border: none;
		font-family: 'Fira Code', monospace;
	}
	.right table tr:first-child td:last-child::after {
		content: "";
	}
	.right table td:last-child::after {
		content: "¶";
		color: #777;
	}
	.right table tr.blur td:last-child::after,
	.blur {
		color: transparent; /* Make the text itself invisible */
		text-shadow: 0 0 8px rgba(240, 248, 255, 0.8); /* Apply a shadow to create the blur */
	}
	/* Editor */
	table {
		background-color: #1f1f1f;
		border: 1px solid lightslategray !important;
		font-family: 'Fira Code', monospace;
	}
	table tr:not(.blur) td:first-child {
		color: lightslategray;
	}
	table tr:first-child {
		border-bottom: 1px solid lightslategray;
	}
	table tr td:first-child {
		border-left: 1px solid lightslategray;
		border-right: 1px solid lightslategray;
	}
	table tr td:last-child {
		cursor: text;
	}
</style>

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
		<table>
			<tr><td colspan="2" style="text-align: left;">
				<svg xmlns="http://www.w3.org/2000/svg" width="16" height="16" fill="none" stroke="currentColor" stroke-width="2" stroke-linecap="round" stroke-linejoin="round">
					<line x1="3" y1="4" x2="16" y2="4"/>
					<line x1="3" y1="8" x2="11" y2="8"/>
					<line x1="3" y1="12" x2="16" y2="12"/>
					<line x1="3" y1="16" x2="11" y2="16"/>
				</svg>
			   	measurements.txt</td></tr>
			<tr><td>1</td><td>Ottawa;14.2</td></tr>
			<tr><td>2</td><td>Bangkok;44.0</td></tr>
			<tr><td>3</td><td>Sydney;-2.5</td></tr>
			<tr><td>4</td><td>Łódź;-20.4</td></tr>
			<tr class="blur"><td>455</td><td>New York;20.7</td></tr>
			<tr class="blur"><td>15,466</td><td>Mars;-24.7</td></tr>
			<tr class="blur"><td>1,546,536</td><td>Venus;84.5</td></tr>
			<tr><td>1,000,000,000</td><td>Budapest;14.9</td></tr>
		</table>
	</div>
</ContentPage>
<NavigationBar
	prevLink="/overview.html"
/>