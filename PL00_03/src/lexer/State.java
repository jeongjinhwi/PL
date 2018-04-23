package lexer;

import static lexer.TokenType.ID;
import static lexer.TokenType.INT;
import static lexer.TransitionOutput.GOTO_ACCEPT_ID;
import static lexer.TransitionOutput.GOTO_ACCEPT_INT;
import static lexer.TransitionOutput.GOTO_EOS;
import static lexer.TransitionOutput.GOTO_FAILED;
import static lexer.TransitionOutput.GOTO_MATCHED;
import static lexer.TransitionOutput.GOTO_SIGN;
import static lexer.TransitionOutput.GOTO_START;
import static lexer.TransitionOutput.GOTO_SHARP; //#T와 #F를 나타내기 위해 상태를 추가한다.

enum State {
	START { // 변환 시작부분
		@Override
		public TransitionOutput transit(ScanContext context) {
			Char ch = context.getCharStream().nextChar(); // 다음char를 읽음
			char v = ch.value();
			switch (ch.type()) {
			case LETTER: // 들어온 char가 문자이면 accept_id로 보낸다
				context.append(v);
				return GOTO_ACCEPT_ID;
			case DIGIT: // 들어온 char가 숫자이면 accept_digit로 보낸다
				context.append(v);
				return GOTO_ACCEPT_INT;
			case SPECIAL_CHAR: // (, ), *, /, >, <, =, \, +, - 이면 Sign으로 보낸다.
				context.append(v);
				if (v == '#') { // 그러나 #은 직후에 T나 F가 나와야 하므로 SHARP로 가는 걸 만든다
					return GOTO_SHARP;
				}
				context.getCharStream().pushBack(v); // pushBack을 사용하여 뒤로 한칸
														// 이동한다. 뒤로 한칸 이동하는 이유는
														// #T나 #F는 띄어쓰기가 없이
														// 바로 이어지므로 pushBack으로
														// 뒤로 한칸 미루어준다.
				return GOTO_SIGN;
			case WS: // 띄어쓰기가 나오면 다시 START로 간다.
				return GOTO_START;
			case END_OF_STREAM: // STREAM이 끝나면 EOS로 간다.
				return GOTO_EOS;
			default:
				throw new AssertionError();
			}
		}
	},
	ACCEPT_ID { // Letter로 인식한후 ID로 접근
		@Override
		public TransitionOutput transit(ScanContext context) {
			Char ch = context.getCharStream().nextChar(); // 다음char를 읽음
			char v = ch.value();
			switch (ch.type()) {
			case LETTER:
			case DIGIT:
				context.append(v);
				return GOTO_ACCEPT_ID; // letter로 인식한후 다시 문자나 숫자를 읽으면 id로 인식한다.
			case SPECIAL_CHAR: // 특수 문자로 인식
				if (v == '?') { // 인식된 char가 ?이면
					context.append(v);
					return GOTO_ACCEPT_ID; // 다시 ID로 접근한다.
				}
				return GOTO_FAILED; // 아니면 FAILED로 간다.
			case WS: // 띄어쓰기나 끝나면 ofName함수를 이용하여 keyword를 인식하게 한다.
			case END_OF_STREAM:
				return GOTO_MATCHED(Token.ofName(context.getLexime()));
			default:
				throw new AssertionError();
			}
		}
	},
	ACCEPT_INT { // DIGIT로 인식한후 INT로 접근
		@Override
		public TransitionOutput transit(ScanContext context) {
			Char ch = context.getCharStream().nextChar(); // 다음 char를 읽음
			switch (ch.type()) {
			case LETTER: // 문자가 나오면 failed로 간다.
				return GOTO_FAILED;
			case DIGIT: // 숫자가 나오면 int로 간다.
				context.append(ch.value());
				return GOTO_ACCEPT_INT;
			case SPECIAL_CHAR: // 특수문자가 나오면 failed로 간다.
				return GOTO_FAILED;
			case WS: // 띄어쓰기나 끝나면 int에 match해주고 문장을 넣어준다.
			case END_OF_STREAM:
				return GOTO_MATCHED(INT, context.getLexime());
			default:
				throw new AssertionError();
			}
		}
	},
	SIGN { // 특수문자로 인식한후 SIGN으로 접근
		@Override
		public TransitionOutput transit(ScanContext context) {
			Char ch = context.getCharStream().nextChar(); // 다음 char를 읽고 그 다음
															// char도 읽는다. 이유는 특수문자가 나온후 바로 띄어 쓰기가 나오기 때문이다.
			Char ch_next = context.getCharStream().nextChar();
			char v = ch.value();
			switch (ch_next.type()) { // ch_next의 타입을 조건으로 사용한다.
			case LETTER: // 문자가 나오면 failed로 간다.
				return GOTO_FAILED;
			case DIGIT: // 숫자가 나오면 digit로 간다.
				context.append(ch_next.value());
				return GOTO_ACCEPT_INT;
			case SPECIAL_CHAR: // 특수문자가 나오면 failed로 간다.
				return GOTO_FAILED;
			case WS: // 띄어쓰기가 나오거나 끝나면 TokenType의 fromSpecialCharactor함수에서 조건을
						// 달아준 문자를 match시킨다.
			case END_OF_STREAM:
				return GOTO_MATCHED(TokenType.fromSpecialCharactor(v), context.getLexime());
			default:
				throw new AssertionError();
			}
		}
	},
	SHARP { // 특수문자가 SHARP인 경우
		@Override
		public TransitionOutput transit(ScanContext context) {
			Char ch = context.getCharStream().nextChar();
			char v = ch.value();
			switch (ch.type()) {
			case LETTER: // #이 나온다음
				if (v == 'T') { // T가 나오면 TokenType의 TRUE를 match시킨다.
					context.append(v);
					return GOTO_MATCHED(TokenType.TRUE, context.getLexime());
				} else if (v == 'F') { // F가 나오면 TokenType의 FALSE를 match시킨다.
					context.append(v);
					return GOTO_MATCHED(TokenType.FALSE, context.getLexime());
				}
				return GOTO_FAILED; // T나 F가 아니면 failed를 나타낸다.
			case DIGIT: // #다음에 숫자, 특수문자, 띄어쓰기가 나오거나 끝나면 failed로 간다.
			case SPECIAL_CHAR:
			case WS:
			case END_OF_STREAM:
				return GOTO_FAILED;
			default:
				throw new AssertionError();
			}
		}
	},
	MATCHED {
		@Override
		public TransitionOutput transit(ScanContext context) {
			throw new IllegalStateException("at final state");
		}
	},
	FAILED {
		@Override
		public TransitionOutput transit(ScanContext context) {
			throw new IllegalStateException("at final state");
		}
	},
	EOS {
		@Override
		public TransitionOutput transit(ScanContext context) {
			return GOTO_EOS;
		}
	};

	abstract TransitionOutput transit(ScanContext context);
}
