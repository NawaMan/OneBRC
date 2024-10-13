<script>
import Label from "$lib/components/Label.svelte";
import NavigationBar from "$lib/components/NavigationBar.svelte";
import ContentPage from "$lib/templates/ContentPage.svelte";

let hoveredTextFile      = false;
let hoveredOneBillionRow = false;
let hoveredStationName   = false;
let hoveredTemperature   = false;
let hoveredNewLine       = false;
let hoveredSemicolon     = false;
let hoveredNegative      = false;
let hoveredTwoDigits     = false;
let hoveredOneDigits     = false;

</script>

<ContentPage
	title="One Billion Row Challenge">
	<div class="left">
		<ul>
			<li>Read a <Label bind:isHovered={hoveredTextFile}>text file</Label> containing <Label bind:isHovered={hoveredOneBillionRow}>1 billion rows</Label> of measurements.</li>
			<li>Each line contains a weather <Label bind:isHovered={hoveredStationName}>station name</Label> and <Label bind:isHovered={hoveredTemperature}>temperature</Label>:
				<ul>
					<li>Rows are delimited by a newline character (<Label bind:isHovered={hoveredNewLine}>'\n'</Label>).</li>
					<li>Station name and temperature are separated by a <Label bind:isHovered={hoveredSemicolon}>semicolon</Label>.</li>
					<li>Station name is encoded in <b>UTF-8</b> and is <b>100 bytes</b> or shorter.</li>
					<li>The temperature ranges from -99.9 to 99.9 
						(<Label bind:isHovered={hoveredTwoDigits}>1-2 digits before</Label> the decimal point, 
						<Label bind:isHovered={hoveredOneDigits}>1 digit after</Label> and an optional 
						<Label bind:isHovered={hoveredNegative}>negative sign</Label>).
					</li>
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
		<div class="tab" class:highlight={hoveredTextFile}>
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
				<div class:highlight={hoveredOneBillionRow}>1,000,000,000</div>
			</td>
			<td class="code">
				<div><span class:highlight={hoveredStationName}>Ottawa</span><span class:highlight={hoveredSemicolon}>;</span><span class:highlight={hoveredTemperature}>14.2</span><span class="newline" class:highlight={hoveredNewLine}>¶</span></div>
				<div><span class:highlight={hoveredStationName}>Bangkok</span><span class:highlight={hoveredSemicolon}>;</span><span class:highlight={hoveredTemperature}>44.0</span><span class="newline" class:highlight={hoveredNewLine}>¶</span></div>
				<div><span class:highlight={hoveredStationName}>Sydney</span><span class:highlight={hoveredSemicolon}>;</span><span class:highlight={hoveredTemperature}>-2.5</span><span class="newline" class:highlight={hoveredNewLine}>¶</span></div>
				<div><span class:highlight={hoveredStationName}>Łódź</span><span class:highlight={hoveredSemicolon}>;</span><span class:highlight={hoveredTemperature}>-20.4</span><span class="newline" class:highlight={hoveredNewLine}>¶</span></div>
				<div class="blur"><span class:highlight={hoveredStationName}>Mercury</span><span class:highlight={hoveredSemicolon}>;</span><span class:highlight={hoveredTemperature}>-50.7</span><span class="newline" class:highlight={hoveredNewLine}>¶</span></div>
				<div class="blur"><span class:highlight={hoveredStationName}>Mars</span><span class:highlight={hoveredSemicolon}>;</span><span class:highlight={hoveredTemperature}>-4.7</span><span class="newline" class:highlight={hoveredNewLine}>¶</span></div>
				<div class="blur"><span class:highlight={hoveredStationName}>Venus</span><span class:highlight={hoveredSemicolon}>;</span><span class:highlight={hoveredTemperature}>84.5</span><span class="newline" class:highlight={hoveredNewLine}>¶</span></div>
				<div class:highlight={hoveredOneBillionRow}><span class:highlight={hoveredStationName}>Budapest</span><span class:highlight={hoveredSemicolon}>;</span><span class:highlight={hoveredTemperature}>14.9</span><span class="newline" class:highlight={hoveredNewLine}>¶</span></div>
			</td>
			</tr>
		</table>
		<p>
			File size: <b>13GB</b>
		</p>
		Digits
		<table border=1 class="temperature-table">
			<tr>
				<td><span class:highlight={hoveredTwoDigits}>&nbsp;1</span>.<span class:highlight={hoveredOneDigits}>2</span></td>
				<td><span class:highlight={hoveredTwoDigits}>&nbsp;12</span>.<span class:highlight={hoveredOneDigits}>3</span></td>
			</tr>
			<tr>
				<td><span class:highlight={hoveredNegative}>-</span><span class:highlight={hoveredTwoDigits}>1</span>.<span class:highlight={hoveredOneDigits}>2</span></td>
				<td><span class:highlight={hoveredNegative}>-</span><span class:highlight={hoveredTwoDigits}>12</span>.<span class:highlight={hoveredOneDigits}>3</span></td>
			</tr>
		</table>
	</div>
</ContentPage>
<NavigationBar
	prevLink="./results.html"
	nextLink="./solution.html"
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
	.newline {
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

	.highlight {
		animation: highlight-animation 1s ease-in-out infinite alternate;
	}
	@keyframes highlight-animation {
		0% {
			color: #C0F1FF;
			filter: brightness(1.5);
			box-shadow: 0 0 10px 10px rgba(150, 255, 153, 0.5);
			text-shadow: 0 0 20px 20px rgba(150, 255, 153, 0.5);
		}
		100% {
			color: #C0FFCB;
			filter: brightness(1);
			/* box-shadow: 0 0 0 0 rgba(150, 255, 255, 0.4); */
			text-shadow: 0 0 0 0 rgba(150, 255, 153, 0.4);
		}
	}

	.temperature-table tr td {
		padding-left: 0.5em;
		padding-right: 0.5em;
		font-family: 'Courier New', Courier, monospace;
	}
</style>
