package com.chess.engine.pieces;

import com.chess.engine.Alliance;
import com.chess.engine.board.Board;
import com.chess.engine.board.BoardUtils;
import com.chess.engine.board.Move;
import com.chess.engine.board.Tile;
import com.google.common.collect.ImmutableList;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import static com.chess.engine.board.Move.*;

public class Knight extends Piece{

    private final static int[] CANDIDATE_MOVE_COORDINATE = {-17, -15, -10, -6, 6, 10, 15, 17};

    /**
     * Constructor
     * @param piecePosition
     * @param pieceAlliance
     */
    public Knight(final int piecePosition, final Alliance pieceAlliance) {
        super( PieceType.KNIGHT, piecePosition, pieceAlliance);
    }

    /**
     * Calculates the legal moves for the knight.
     *
     * @param board
     * @return
     */
    @Override
    public Collection<Move> calculateLegalMoves(final Board board) {

        final List<Move> legalMoves = new ArrayList<>();

        for(final int currentCandidateOffset : CANDIDATE_MOVE_COORDINATE) {
            final int candidateDestinationCoordinate = this.piecePosition + currentCandidateOffset;
            if(BoardUtils.isValidTileCoordinate(candidateDestinationCoordinate)) {

                if(isFirstColumnExclusion(this.piecePosition, currentCandidateOffset) ||
                        isSecondColumnExclusion(this.piecePosition, currentCandidateOffset) ||
                        isSeventhColumnExclusion(this.piecePosition, currentCandidateOffset) ||
                        isEighthColumnExclusion(this.piecePosition, currentCandidateOffset)) {
                    continue;
                }

                final Tile candidateDestinationTile = board.getTile(candidateDestinationCoordinate);
                if(!candidateDestinationTile.isTileOccupied()) {
                    legalMoves.add(new MajorMove(board, this, candidateDestinationCoordinate));
                } else {
                    final Piece pieceAtDestination = candidateDestinationTile.getPiece();
                    final Alliance pieceAlliance = pieceAtDestination.getPieceAlliance();
                    //Enemy piece
                    if(this.pieceAlliance != pieceAlliance) {
                        legalMoves.add(new AttackMove(board, this, candidateDestinationCoordinate, pieceAtDestination));
                    }
                }
            }
        }
        return ImmutableList.copyOf(legalMoves);
    }

    @Override
    public String toString(){
        return PieceType.KNIGHT.toString();
    }

    private boolean isEighthColumnExclusion(int piecePosition, int candidateOffset) {
        return BoardUtils.EIGHTH_COLUMN[piecePosition] && (candidateOffset == -6 || candidateOffset == 10 ||
                candidateOffset == 17);
    }

    private boolean isSeventhColumnExclusion(int currentPosition, int CandidateOffset) {
        return BoardUtils.SEVENTH_COLUMN[currentPosition] && (CandidateOffset == -17 || CandidateOffset == -10 ||
                CandidateOffset == 6 || CandidateOffset == 15);
    }

    private boolean isSecondColumnExclusion(int piecePosition, int candidateOffset) {
        return BoardUtils.SECOND_COLUMN[piecePosition] && (candidateOffset == -17 || candidateOffset == -10 ||
                candidateOffset == 6 || candidateOffset == 15);
    }

    private static boolean isFirstColumnExclusion(final int currentPosition, final int candidateOffset) {
        return BoardUtils.FIRST_COLUMN[currentPosition] && (candidateOffset == -17 || candidateOffset == -10 ||
                candidateOffset == 6 || candidateOffset == 15);
    }


}
